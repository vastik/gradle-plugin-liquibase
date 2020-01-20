package com.vast.gradle.plugin.liquibase

class ChangelogBuilderExtension {
    String includeDirectory = 'src/main/resources/db/migration/include'
    String changelogFile = 'src/main/resources/db/migration/changelog.yaml'
    String author = 'generated'
}