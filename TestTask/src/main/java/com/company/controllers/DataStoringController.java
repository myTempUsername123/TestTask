package com.company.controllers;

import com.company.models.UrlDto;
import com.company.repositories.FilesAndUrlsRepository;
import com.company.services.FileService;
import com.company.services.UrlService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("url")
public class DataStoringController {

    private FilesAndUrlsRepository repository = new FilesAndUrlsRepository();
    private FileService fileService = new FileService(repository);
    private UrlService urlService = new UrlService(repository, fileService);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UrlDto> listUrls(){
        return urlService.getAllUrls();
    }

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getFileById(@PathParam("id") long id){
        return Response.ok(fileService.getFileById(id), MediaType.APPLICATION_OCTET_STREAM)
                .build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response  addUrlToDownload(String url) {
        String s = urlService.addUrlAndStartDownloading(url);
        return Response.ok(s).build();
    }

    @POST
    @Path("/list")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addListOfUrlsToDownload(List<String> urls){
        return Response.ok(urlService.addListOfUrlsAndStartDownloading(urls)).build();
    }

}
