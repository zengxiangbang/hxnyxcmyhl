pipeline{
       //定义参数化构建
     parameters {		 
		 string(name: 'origin_repo', defaultValue: 'harbor2.mail.10086.cn', description: 'docker私仓地址')
         string(name: 'repo', defaultValue: "${JOB_NAME}", description: 'docker镜像名')
         string( name: 'giturl', defaultValue: "https://gitlab.mail.10086.cn/zengxiangbang/hxnyxcmyhl.git", 
			     description: '使用https时，不需要ssh密钥,适合基于kubernetes的jnlp。如：https://gitlab.mail.10086.cn/zengxiangbang/demo.git ')
         choice( name: 'branch', choices: ['test','prod'], 
			     description: '测试线,  test分支\n生产线,  prod分支')
        }
      // 定义groovy脚本中使用的环境变量
     environment{
        // 本示例中使用DEPLOY_TO_K8S变量来决定把应用部署到哪套容器集群环境中，如“prod Environment”， “test Environment”等
		// 三种定义环境变量的方法
        // IMAGE_TAG =  sh(returnStdout: true,script: 'echo $image_tag').trim()
		// ENVS = "${params.envs}"  //此参数来自于上边的parameters”
		// def GIT_URL = 'https://gitlab.mail.10086.cn/zengxiangbang'
        IN_TAG = "${BUILD_NUMBER}"
		IN_REPO = sh(returnStdout: true,script: 'if [ ! -n "$gitlabSourceRepoName" ];then IN_REPO="$repo";else IN_REPO="$gitlabSourceRepoName";fi ;echo $IN_REPO').trim()
		IN_BRANCH = sh(returnStdout: true,script: 'if [ ! -n "$gitlabBranch" ];then IN_BRANCH="$branch";else IN_BRANCH="$gitlabBranch";fi ;echo $IN_BRANCH').trim()
		IN_ENVS = sh(returnStdout: true,script: 'if [ "$branch" == "prod" -o "$gitlabBranch" == "prod" ];then IN_ENVS="prod";elif [ "$branch" == "test" -o "$gitlabBranch" == "test" ];then IN_ENVS="test";else IN_ENVS="$gitlabBranch";fi ;echo $IN_ENVS').trim()
        IN_GIT_URL = sh(returnStdout: true,script: 'if [ ! -n "$gitlabSourceRepoHttpUrl" ];then IN_GIT_URL="$giturl";else IN_GIT_URL="$gitlabSourceRepoHttpUrl";fi ;echo $IN_GIT_URL').trim()
       	IN_GITUSERNAME = sh(returnStdout: true,script: 'if [ ! -n "$gitlabUserName" ];then IN_GITUSERNAME="zengxiangbang";else IN_GITUSERNAME="$gitlabUserName";fi ;echo $IN_GITUSERNAME').trim()
        IN_GITEMAIL = sh(returnStdout: true,script: 'if [ ! -n "$gitlabUserEmail" ];then IN_GITEMAIL="zengxiangbang@richinfo.cn" ;else IN_GITEMAIL="$gitlabUserEmail";fi ;echo $IN_GITEMAIL').trim()
         }
      
      // 定义本次构建使用哪个标签的构建环境，本示例中为 “slave-pipeline”
     agent{
        kubernetes {
            label 'slave-pipeline'
            defaultContainer 'jnlp'
            yaml """
            apiVersion: v1
            kind: Pod
            metadata:
              labels:
                app: slave-pipeline
            spec:
                nodeSelector:
#                  workload_type: spot  #阿里云竞价时使用
                containers:
                - name: kaniko
                  image: registry.cn-beijing.aliyuncs.com/acs-sample/jenkins-slave-kaniko:0.6.0
                  command:
                  - cat
                  tty: true
                  workingDir: '/home/jenkins'
                  volumeMounts:
                  - name: jenkins-docker-cfg
                    mountPath: "/home/jenkins/.docker"
                  env:
                  - name: DOCKER_CONFIG
                    value: /home/jenkins/.docker
                - name: maven
                  image: registry.cn-beijing.aliyuncs.com/acs-sample/jenkins-slave-maven:3.3.9-jdk-8-alpine
                  command:
                  - cat
                  tty: true
                  workingDir: '/home/jenkins'
                - name: kubectl
                  image: registry.cn-beijing.aliyuncs.com/acs-sample/jenkins-slave-kubectl:1.11.5
                  command:
                  - cat
                  tty: true
                  workingDir: '/home/jenkins'
                volumes:
                - name: jenkins-docker-cfg
                  secret:
                    secretName: jenkins-docker-cfg
            """
        }
    }

     // "stages"定义项目构建的多个模块，可以添加多个 “stage”， 可以多个 “stage” 串行或者并行执行
     stages{
        // 定义第一个stage， 完成gitlab环境配置的任务
         stage('Git环境'){
             steps{
             // 以下是定义gitlab 帐号密码的两种方法，方法一
                 withCredentials([string(credentialsId: 'gitlab-certs', variable: 'gitcredentials')]) {
                 sh ''' echo "$gitcredentials" > /home/jenkins/.git-credentials ''' 
				 }
			// 以下是定义gitlab 帐号密码的两种方法，方法二
            //   sh "echo 'https://zengxiangbang:www.richinfo139@gitlab.mail.10086.cn' >/home/jenkins/.git-credentials"
			
			//   验证.git-credentials配置文件
			//   sh "ls -al /home/jenkins/&& ls -al /home/jenkins/.git-credentials"
            //   sh "cat /home/jenkins/.git-credentials"
               
               sh """
               git config --global user.email "${IN_GITEMAIL}" 
			   git config --global user.name "${IN_GITUSERNAME}"
			   git config --global credential.helper store –file=.git_credentails
			   """
             }
         }        
        // 定义第二个stage， 完成克隆源码的任务
         stage('Git代码检出'){
             steps{
			 //  git检出的两种方法
             //  git branch: "${IN_BRANCH}", credentialsId: 'gitlab', url: "${IN_GIT_URL}"
                 checkout([$class: 'GitSCM', branches: [[name: "${IN_BRANCH}"]], doGenerateSubmoduleConfigurations: false,
                 extensions: [[$class: 'UserIdentity', email: "${IN_GITEMAIL}", name: "${IN_GITUSERNAME}"],
                 [$class: 'PerBuildTag']], 
                 userRemoteConfigs: [[url: "${IN_GIT_URL}"]]])
             }
         }
         // 定义第三个stage， 完成源码的静态测试任务
	     stage('静态测试'){
	         when {
                 expression {
                     "$IN_BRANCH" == "test"
                 }
             }
		     steps{
                 container("maven") {
				     // 静态测试的相关步骤
                     sh "mvn clean compile findbugs:findbugs pmd:pmd checkstyle:checkstyle -B -P ${IN_ENVS}"
                     checkstyle canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/checkstyle-result.xml', unHealthy: ''
                     findbugs canComputeNew: false, defaultEncoding: '', excludePattern: '', healthy: '', includePattern: '', pattern: '**/findbugsXml.xml', unHealthy: ''
                     pmd canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/pmd.xml', unHealthy: ''
                     step([$class: 'AnalysisPublisher', canComputeNew: false, defaultEncoding: '', healthy: '', unHealthy: ''])}
			 }
		 }	
		 
        // 添加第四个stage， 运行源码打包命令
         stage('mvn 打包'){
		     steps{
                 container("maven") {
                     sh "mvn package -B -DskipTests -P ${IN_ENVS}"
			     }
			 }
		 }

        // 添加第五个stage, 运行容器镜像构建和推送命令， 用到了environment中定义的groovy环境变量和jenkins内部变量
         stage('Image Build And Publish'){
             steps{
                 container("kaniko") {
                     sh "kaniko -f `pwd`/Dockerfile -c `pwd` --destination=${ORIGIN_REPO}/${IN_ENVS}/${IN_REPO}:${IN_TAG}"
                 }
            }
         }
		 
		 // 添加第六个stage,部暑到kubernetes
         stage('部暑到kubernetes') {
         parallel {
             stage('部暑到prod生产环境') {
             when {
                 expression {
                     "$IN_BRANCH" == "prod"
                 }
             }
                 steps {
                     sh "env"
                     container('kubectl') {
                         step([$class: 'KubernetesDeploy', 
						     context: [configs: 'deployment.yaml', 
							 dockerCredentials: [[credentialsId: 'harbor2-repos', url: 'https://harbor2.mail.10086.cn']], 
							 kubeConfig: [path: ''], 
							 kubeconfigId: 'k8sCertAuth', secretName: 'harborsecret']])  
                     // 因kubernetesDeploy不支持HPA,使用kubectl创建HPA，使用到kubeconfig配置
                     //  sh "env"
                         withCredentials([kubeconfigContent(credentialsId: 'k8sCertAuth', variable: 'KUBECONFIG_CONTENT')]) {
                             sh '''
							 echo "$KUBECONFIG_CONTENT" > kubeconfig && export KUBECONFIG=./kubeconfig && cat kubeconfig 
                             if [ "`kubectl get hpa -n ${IN_ENVS} ${IN_REPO}|wc -l`" -eq 0 ];then kubectl autoscale deployment ${IN_ENVS}-${IN_REPO} --min=2 --max=10 -n prod  --cpu-percent=50; else exit 0; fi 
                             '''
                         }
                     
                     }
                 }
             }
             stage('发布到测试环境') {
             when {
                 expression {
                     "$IN_BRANCH" == "test"
                 }
             }
             steps {
                 container('kubectl') {
                     step([$class: 'KubernetesDeploy', context: [configs: 'deployment.yaml', dockerCredentials: [[credentialsId: 'harbor2-repos', url: 'https://harbor2.mail.10086.cn']], kubeConfig: [path: ''], kubeconfigId: 'k8sCertAuth', secretName: 'harborsecret']])
                 }
             }
             }
         }
         }
		 
         // 添加第七个stage, 完成合并和master分支打tag任务
         stage('prod分支合并到master，并打tag') {
	         environment{
			     // veriosn number获取的两种方法,使用 version number 插件自定义tag格式，也可以使用 gitlab plugin自带的固定格式$BUILD_TAG ，
	             def  version = VersionNumber projectStartDate: '2019-01-01', versionNumberString: '${IN_REPO}_${IN_BRANCH}_${BUILD_DATE_FORMATTED,"yyyyMMdd"}.${BUILDS_ALL_TIME}', versionPrefix: '', worstResultForIncrement: 'SUCCESS'
	         }
             when {
                 expression {
                       "$IN_BRANCH" == "prod"
                 }
             }
             steps{
			 //检出master分支
                 git branch: "master", credentialsId: 'gitlab', url: "${IN_GIT_URL}"
             // 查看版本号
 			     sh "echo $version && echo $BUILD_TAG"
			 // 合并prod分支到master
                 sh """ 
                 git checkout master
				 git merge origin/$IN_BRANCH -m "$IN_BRANCH `date +"%Y%m%d"`" 
				 git push origin master
				 """
			 // 打tag有两种方法，在上边的git checkout 中class定义 PerBuildTag
			 //	 sh """
   			 //     git tag -a $version -m '提交人: '${IN_GITUSERNAME}
    		 //	 git push -u origin master --tags
		     //    """
             }
         }
      }
	  
	 // 第八步，定义发送邮件
     post {
         success {
        //   sh "env"
		//   使用JELLY模板，可查jenkins/plugins/email-ext/WEB-INF/lib/email-ext.jar的tempalte
        //   emailext body: '${JELLY_SCRIPT,template="static-analysis"}', 
             emailext body: '${DEFAULT_CONTENT}',
			    subject: '构建通知:${BUILD_STATUS} - ${PROJECT_NAME} - Build # ${BUILD_NUMBER} !',
				to: "${IN_GITEMAIL}",
				recipientProviders: [
//				    [$class: 'CulpritsRecipientProvider'],
                    [$class: 'DevelopersRecipientProvider'],
//                  [$class: 'RequesterRecipientProvider']
				    ]
                }   
        failure {
             emailext body: '${JELLY_SCRIPT,template="static-analysis"}', 
			    subject: '构建通知:${BUILD_STATUS} - ${PROJECT_NAME} - Build # ${BUILD_NUMBER} !',
				to: "${IN_GITEMAIL}"
         }
     }
}

