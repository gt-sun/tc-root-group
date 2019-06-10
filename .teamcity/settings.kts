import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.script

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2019.1"

project {

    template(BeforeScript)

    params {
        text("env.cnjbossqa01_user", "carl", readOnly = true, allowEmpty = false)
        password("env.cnjbossqa01_pwd", "credentialsJSON:f5f798f4-9e4b-4a90-a8f2-2e240c2142d9", display = ParameterDisplay.HIDDEN, readOnly = true)
    }
}

object BeforeScript : Template({
    name = "before_script"

    steps {
        script {
            name = "check_user_premission"
            id = "RUNNER_6"
            scriptContent = """
                echo %teamcity.build.triggeredBy.username%
                if [ "%teamcity.build.triggeredBy.username%" != "a9k47zz" ] && [ "%teamcity.build.triggeredBy.username%" != "admin" ]; then echo -e "\033[41;37m You don't have permission to execute this!! EXIT... \033[0m"; exit 1; fi
            """.trimIndent()
        }
    }
})
