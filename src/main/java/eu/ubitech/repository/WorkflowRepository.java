package eu.ubitech.repository;

import eu.ubitech.model.Workflow;
import io.quarkus.mongodb.panache.PanacheMongoRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WorkflowRepository implements PanacheMongoRepository<Workflow> {

    public Workflow findByName(String name){
        return find("name", name).firstResult();
    }

    public void storeWorkflow(Workflow workflow) {
        persist(workflow);
    }

}
