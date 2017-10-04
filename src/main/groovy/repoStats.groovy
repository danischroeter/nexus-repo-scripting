import org.sonatype.nexus.repository.storage.Query
import org.sonatype.nexus.repository.storage.StorageFacet

import groovy.json.JsonOutput

class Repo{
    String name
    Long componentCount

    Repo(name,componentCount){
        this.name = name
        this.componentCount = componentCount
    }
}

log.info("Listing repo stats")

repos = repository.repositoryManager.browse().flatten {repo ->
    StorageFacet storageFacet = repo.facet(StorageFacet)
    def tx = storageFacet.txSupplier().get()
    tx.begin()
    count = tx.countComponents(Query.builder().build(), [repo])
    res = new Repo(repo.name,count)
    tx.commit()
    res
}


def result = JsonOutput.toJson([
        repos: repos
])
return result