import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.sonatype.nexus.repository.storage.Component
import org.sonatype.nexus.repository.storage.Query
import org.sonatype.nexus.repository.storage.StorageFacet

def request = new JsonSlurper().parseText(args)
assert request.repoName: 'repoName parameter is required'
assert request.beforeDate: 'beforeDate parameter is required, format: yyyy-mm-dd'


log.info("delete components for repository: ${request.repoName} updated before: ${request.beforeDate}")

def compInfo = { Component c -> "${c.group()}:${c.name()}:${c.version()}[${c.lastUpdated()}]}" }

def repo = repository.repositoryManager.get(request.repoName)
StorageFacet storageFacet = repo.facet(StorageFacet)

def tx = storageFacet.txSupplier().get()
tx.begin()
Iterable<Component> components = tx.findComponents(Query.builder().where('last_updated < ').param(request.beforeDate).build(), [repo])
tx.commit()
tx.close()

if(request.delete == "true"){
    log.info("about to delete " + components.flatten(compInfo))
    for(Component c : components) {
        log.delete("deleting " + compInfo(c))
        tx2 = storageFacet.txSupplier().get()
        tx2.begin()
        tx2.deleteComponent(c)
        tx2.commit()
        tx2.close()
    }
    log.info("finished deleting " + components.flatten(compInfo))
}
else {
    log.info("components that would be deleted: "+components.size() + "\n"+components.flatten(compInfo))
}



def result = JsonOutput.toJson([
        comps: components.flatten(compInfo),
        count: components.size()
])
return result