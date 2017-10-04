import groovy.json.JsonSlurper
import org.sonatype.nexus.repository.storage.Component
import org.sonatype.nexus.repository.storage.Query
import org.sonatype.nexus.repository.storage.StorageFacet

import groovy.json.JsonOutput

def request = new JsonSlurper().parseText(args)
assert request.repoName: 'repoName parameter is required'

log.info("Component stats for repository: ${request.repoName}")

def repo = repository.repositoryManager.get(request.repoName)
StorageFacet storageFacet = repo.facet(StorageFacet)

def tx = storageFacet.txSupplier().get()
tx.begin()
Iterable<Component> components = tx.findComponents(Query.builder().build(), [repo])
tx.commit()
tx.close()

def compsCountsGrouped = components.groupBy {it.lastUpdated().getYear()}{it.lastUpdated().getMonthOfYear()}.collectEntries{ o->
    [o.key, (1..12).collectEntries{
        cInMonth = o.value.get(it)
        [it, cInMonth ? cInMonth.size() : 0]
    }]
}

def compsArtifactsGrouped = components.groupBy {it.group()+":"+it.name()} collectEntries{ o -> [o.key,o.getValue().size()] }

def result = JsonOutput.toJson([
        compsGrouped: compsArtifactsGrouped,
        compsCountsGrouped: compsCountsGrouped
])
return result