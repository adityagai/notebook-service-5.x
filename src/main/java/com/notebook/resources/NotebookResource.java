package com.notebook.resources;

import com.notebook.api.Notebook;
import com.notebook.core.NotebookStatus;
import com.notebook.db.NotebookDAO;
import io.dropwizard.hibernate.UnitOfWork;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/notebooks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotebookResource {

    private final NotebookDAO dao;

    public NotebookResource(NotebookDAO dao) {
        this.dao = dao;
    }

    @POST
    @UnitOfWork
    public Response create(@Valid Notebook notebook) {
        Notebook created = dao.create(notebook);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @GET
    @UnitOfWork(readOnly = true)
    public List<Notebook> listAll() {
        return dao.findAll();
    }

    @GET
    @Path("/{id}")
    @UnitOfWork(readOnly = true)
    public Notebook getById(@PathParam("id") Long id) {
        return dao.findById(id)
                .orElseThrow(() -> new NotFoundException("Notebook not found: " + id));
    }

    @PUT
    @Path("/{id}")
    @UnitOfWork
    public Notebook update(@PathParam("id") Long id, @Valid Notebook updated) {
        Notebook existing = dao.findById(id)
                .orElseThrow(() -> new NotFoundException("Notebook not found: " + id));
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        if (updated.getStatus() != null) {
            existing.setStatus(updated.getStatus());
        }
        return dao.update(existing);
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public Response delete(@PathParam("id") Long id) {
        Notebook existing = dao.findById(id)
                .orElseThrow(() -> new NotFoundException("Notebook not found: " + id));
        dao.delete(existing);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{id}/archive")
    @UnitOfWork
    public Notebook archive(@PathParam("id") Long id) {
        Notebook existing = dao.findById(id)
                .orElseThrow(() -> new NotFoundException("Notebook not found: " + id));
        existing.setStatus(NotebookStatus.ARCHIVED);
        return dao.update(existing);
    }
}
