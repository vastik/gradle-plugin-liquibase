package com.vast.gradle.plugin.liquibase

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml

class ChangelogBuilderPlugin implements Plugin<Project> {
    void apply(Project project) {
        def extension = project.extensions.create('databaseBuildChangelog', ChangelogBuilderExtension)
        project.task('databaseBuildChangelog') { task ->
            task.group = "database"
            task.doLast {
                def databaseChangeLog = []
                def changeSet = [id: 'version 1', author: extension.author, tagDatabase: 'Version 1']
                databaseChangeLog << [changeSet: changeSet]

                project.fileTree(extension.includeDirectory).sort().each {
                    file ->
                        def include = [file: 'include/' + file.name, relativeToChangelogFile: true]
                        databaseChangeLog << [include: include]
                }

                def map = [databaseChangeLog: databaseChangeLog]

                def options = new DumperOptions()
                options.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
                options.prettyFlow = true

                def yaml = new Yaml(options)
                def writer = new FileWriter(extension.changelogFile)
                yaml.dump(map, writer)
            }
        }
    }
}