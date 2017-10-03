import org.sonatype.nexus.repository.storage.Asset
import org.sonatype.nexus.repository.storage.Query
import org.sonatype.nexus.repository.storage.StorageFacet

import groovy.json.JsonOutput
import groovy.json.JsonSlurper


//from https://gist.github.com/kellyrob99/2d1483828c5de0e41732327ded3ab224


def request = new JsonSlurper().parseText(args)
assert request.repoName: 'repoName parameter is required'
assert request.startDate: 'startDate parameter is required, format: yyyy-mm-dd'

log.info("Gathering Asset list for repository: ${request.repoName} as of startDate: ${request.startDate}")

def repo = repository.repositoryManager.get(request.repoName)
StorageFacet storageFacet = repo.facet(StorageFacet)
def tx = storageFacet.txSupplier().get()

tx.begin()

Iterable<Asset> assets = tx.
        findAssets(Query.builder().where('last_updated > ').param(request.startDate).build(), [repo])
def urls = assets.collect { "/repository/${repo.name}/${it.name()}" }

tx.commit()

def result = JsonOutput.toJson([
        assets  : urls,
        since   : request.startDate,
        repoName: request.repoName
])
return result