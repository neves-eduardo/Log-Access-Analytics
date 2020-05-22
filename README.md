# Log-Access-Analytics
### Infra scripts repository link: https://github.com/neves-eduardo/LAA-Infra
### Setting up 
- In the AWS EC2 dashboard create a keypair named 'laachallenge1'
- Clone the Infra Scripts repo.
- Build the Jenkins AMI with 'Packer build jenkinsprovision.json'
- Ansible will print the Jenkins admin password at the end of the job, copy this key for later
- Launch the AMI in an instance. The only special thing you need to do in this step is create a security group with a TCP rule like this
  Custom TCP|TCP|8080|0.0.0.0/0
- In your browser access the following address {instance public DNS}:8080
- Paste the Jenkins admin key here, Jenkins will start configuring itself!


### Jenkins plugins:
  - Recommended Plugins in the standard installation proccess
  - Amazon EC2 Plugin
  - Ansible Plugin
  - Copy Artifact Plugin
  - Packer plugin
  - Gradle Plugin
  - Terraform Plugin
  
### Jenkins Configuration
After installing the Jenkins plugins, we need to configure our jenkins plugins. 
  - Go to "Manage Jenkins" and "Global Tool Configuration", here you'll need to add a valid installation to the following tools:
    Gradle, Ansible, Packer and Terraform. The installation is pretty straightfoward, for each of these tools, click[Tool Name] Installation > Add [tool name], give a name to the installation
   and select the automatic installation option then select the linux amd64 version if necessary.
   - Go to "Manage Jenkins" > "Credentials" > "Global Credential" > "Jenkins(global)" > "Global Credentials(unrestricted)" > "Add Credentials" > selecionar a opção AWS credentials e inserir as chaves nos campos.
   - Go to "Manage Jenkins" > "Configure System" > "Global Properties" > select the "Environment variables" box, "add" and add a variable named "AWS_DEFAULT_REGION" and with the value "us-east-1"
   
### Importing the Jenkins Jobs
  - Go to "Manage Jenkins" and "Jenkins CLI", download the jenkins-cli .jar.
  - At your local machine cd into the LAA-Infra repo directory. 
  Run the following commands:
    - java -jar jenkins-cli.jar -s http://{Instance Public DNS}:8080/ -auth admin:{your jenkins secret key} create-job "Build_LAA_App" < Build_LAA_App.xml 
    - java -jar jenkins-cli.jar -s http://{Instance Public DNS}:8080/ -auth admin:{your jenkins secret key} create-job "Bake LAA Influx" < Bake_LAA_Influx.xml  
    - java -jar jenkins-cli.jar -s http://{Instance Public DNS}:8080/ -auth admin:{your jenkins secret key} create-job "Bake LAA App" < Bake_LAA_App.xml  
    - java -jar jenkins-cli.jar -s http://{Instance Public DNS}:8080/ -auth admin:{your jenkins secret key} create-job "Start Influx DB" < Start_Influx_DB.xml 
    - java -jar jenkins-cli.jar -s http://{Instance Public DNS}:8080/ -auth admin:{your jenkins secret key} create-job "Terraform Infra UP" < Terraform_Infra_UP.xml
