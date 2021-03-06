
## What is this plugin about?
This plugin is part of the scm-manager (http://www.scm-manager.org) project.

This plugin enables you to protect tags from removal, .i e. once the tags have been pushed to SCM Manager, they cannot be removed.

## How to use this plugin

### NOTE

Admins can always remove tags, as can the Owner within his/her repository.

This plugin has a tiny configuration in the Settings sections of SCM Manager.

### Protection Pattern

This is a wildcard pattern describing the name of the protected tags. You can use 

* _*_ to match one or more characters
* _?_ to match any single character 

This is enables you to protect, for example _releases/*_ or simply _*_ to effectively disable tag removal at all.

Tags with names that are not covered by this pattern are free to be removed by any user with regular write access to the repository.

### Reduce Owner Privilege

With this setting enabled, the Owner of a repository is forced to adhere to the Protection Pattern, as well, i. e. may only remove tags that do not match the Protection Pattern.

## Issues and Suggestions

If you have any questions or suggestions, please submit an issue.