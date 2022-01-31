pipeline {
  agent any
	environment {
	  CODING_MAVEN_REPO_NAME = "maven"
		CODING_MAVEN_REPO_ID = "${CCI_CURRENT_TEAM}-${PROJECT_NAME}-${CODING_MAVEN_REPO_NAME}"
    CODING_MAVEN_REPO_URL = "${CCI_CURRENT_WEB_PROTOCOL}://${CCI_CURRENT_TEAM}-maven.pkg.${CCI_CURRENT_DOMAIN}/repository/${PROJECT_NAME}/${CODING_MAVEN_REPO_NAME}/"
	}
  stages {
    stage('检出') {
      steps {
        checkout([$class: 'GitSCM',
        branches: [[name: GIT_BUILD_REF]],
        userRemoteConfigs: [[
          url: GIT_REPO_URL,
          credentialsId: CREDENTIALS_ID
        ]]])
      }
    }
    stage('编译') {
      steps {
        script {
          if (env.TAG_NAME ==~ /.*/ ) {
	          CODING_ARTIFACT_VERSION = "${env.TAG_NAME}"
          } else if (env.MR_SOURCE_BRANCH ==~ /.*/ ) {
	          CODING_ARTIFACT_VERSION = "${env.MR_RESOURCE_ID}"
          } else {
	          CODING_ARTIFACT_VERSION = "${env.BRANCH_NAME.replace('/', '-')}"
          }
        }
        withCredentials([
          usernamePassword(
            credentialsId: env.CODING_ARTIFACTS_CREDENTIALS_ID,
            usernameVariable: 'CODING_ARTIFACTS_USERNAME',
            passwordVariable: 'CODING_ARTIFACTS_PASSWORD'
          )
        ]) {
          withEnv([
            "CODING_ARTIFACTS_USERNAME=${CODING_ARTIFACTS_USERNAME}",
            "CODING_ARTIFACTS_PASSWORD=${CODING_ARTIFACTS_PASSWORD}",
            "CODING_ARTIFACT_VERSION=${CODING_ARTIFACT_VERSION}"
          ]) {
            sh 'mvn -Pcoding versions:set -DnewVersion=${CODING_ARTIFACT_VERSION} clean package -DskipTests -s ./.coding/settings.xml'
          }
        }
      }
    }
    stage('推送到 Maven 制品库') {
      steps {
        withCredentials([
          usernamePassword(
            credentialsId: env.CODING_ARTIFACTS_CREDENTIALS_ID,
            usernameVariable: 'CODING_ARTIFACTS_USERNAME',
            passwordVariable: 'CODING_ARTIFACTS_PASSWORD'
          )
        ]) {
          withEnv([
            "CODING_ARTIFACTS_USERNAME=${CODING_ARTIFACTS_USERNAME}",
            "CODING_ARTIFACTS_PASSWORD=${CODING_ARTIFACTS_PASSWORD}"
          ]) {
            sh 'mvn -Pcoding deploy -DskipTests -s ./.coding/settings.xml'
          }
        }
      }
    }
  }
}
