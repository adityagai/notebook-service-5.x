package com.notebook.db;

import com.notebook.api.Notebook;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class NotebookDAO extends AbstractDAO<Notebook> {

    public NotebookDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<Notebook> findById(Long id) {
        return Optional.ofNullable(get(id));
    }

    public List<Notebook> findAll() {
        return list(namedTypedQuery("Notebook.findAll"));
    }

    public Notebook create(Notebook notebook) {
        return persist(notebook);
    }

    public Notebook update(Notebook notebook) {
        return persist(notebook);
    }

    public void delete(Notebook notebook) {
        currentSession().remove(notebook);
    }
}
