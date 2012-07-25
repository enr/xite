package com.github.enr.xite.util;

class Configurations {
	
	public static Map getAdditionals(bulk, String sourcePath) {
		// a map of resources directory -> sub directory of destination
		def resourcesDirectories = [:]
		//resourcesDirectories.put(resourcesSourceDirectoryName, '')
		//reporter.out('configuration.resources.sources.additionals: %s', configuration.get('resources.sources.additionals'))
		def adds = [:]
		for (a in bulk) //configuration.getBulk('resources.sources.additionals')) 
		{
			//System.out.printf("additional %s %s%n", a, a.getClass().getName())
			//resourcesDirectories.put(sourcePath + '/' + a.source, a.destination)
			def tokens = a.key.toString().split("\\.");
			//reporter.out('tokens => %s', tokens);
			if (tokens.size() > 0) {
				def additionalId = tokens[0]
				def additionalRole = tokens[1]
				//System.out.printf('id: %s , role: %s%n', additionalId, additionalRole);
				if (!adds[additionalId]) {
					adds[additionalId] = [:]
				}
				adds[additionalId][additionalRole] = a.value
			}
		}
		
		for (ad in adds) {
			//reporter.out('id %s = %s', ad.key, ad.value);
			//System.out.printf('     %s => %s%n', ad.value.source, ad.value.destination);
			resourcesDirectories.put(FilePaths.join(sourcePath, ad.value.source), ad.value.destination)
		}
		return resourcesDirectories;
	}
}