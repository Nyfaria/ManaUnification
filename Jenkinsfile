pipeline {
    agent any

    environment {
        GRADLE_ARGS = ''
        DISCORD_WEBHOOK = credentials('clientele-discord-webhook')
        DISCORD_PING = credentials('clientele-discord-ping')
    }

    stages {
        stage('DiscordNotifyStart') {
            when {
                expression {
                    publishingToMaven()
                }
                not {
                    changeRequest()
                }
            }

            steps {
                script {
                    discord.sendStarted(currentBuild, DISCORD_WEBHOOK)
                }
            }
        }

        stage('Clean') {
            when {
                expression {
                    publishingToMaven()
                }
                not {
                    changeRequest()
                }
            }

            steps {
                cleanWs(patterns: [[pattern: 'build/libs/**', type: 'INCLUDE']])
            }
        }

        stage('Build') {
            when {
                not {
                    changeRequest()
                }
            }

            steps {
                withCredentials([usernamePassword(credentialsId: 'hundred-media-maven-user', usernameVariable: 'MAVEN_USER', passwordVariable: 'MAVEN_PASSWORD')]) {
                    withGradle {
                        sh './gradlew ${GRADLE_ARGS} --refresh-dependencies --continue build'
                        gradleVersion(this)
                    }
                }
            }
        }

        stage('Publish') {
            when {
                expression {
                    publishingToMaven()
                }
                not {
                    changeRequest()
                }
            }

            steps {
                archiveArtifacts artifacts: 'build/libs/**/*.jar', allowEmptyArchive: true, fingerprint: true, onlyIfSuccessful: true
            }
        }
    }

    post {
        always {
            script {
                if (env.CHANGE_ID/* Pull Request ID */ == null && publishingToMaven()) {
                    discord.sendFinishedAndPingOnSuccess(currentBuild, DISCORD_WEBHOOK, DISCORD_PING)
                }
            }
        }
    }
}