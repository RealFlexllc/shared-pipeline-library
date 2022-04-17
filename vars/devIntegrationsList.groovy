def call(){
	def intgrList = 
	[
	$class: 'ChoiceParameter', 
	choiceType: 'PT_MULTI_SELECT', 
	filterLength: 1, 
	filterable: true, 
	name: 'Integrations', 
	randomName: 'choice-parameter-171329671394273', 
	script: [
		$class: 'GroovyScript', 
		fallbackScript: [classpath: [], sandbox: false, script: ''], 
		script: [
			classpath: [], 
			sandbox: false, 
			script: '''
				def list = []
       				def IDCS_TOKEN_URL="https://idcs-01c1e32924fc42c6af9b79b9e3de57f3.identity.oraclecloud.com/oauth2/v1/token"
    				def HEADER_BASIC_AUTH="Authorization: Basic NjA1Y2UxNzgyZjZhNDQ3Mzk1ZWVmMmI4ZTBlN2I3MmE6MDU0MzM4NjgtOGRkMi00MzY4LTkzYmItZDcwZjkxNjFiZjE1"
    				def HEADER_CONTENT_TYPE="Content-Type: application/x-www-form-urlencoded"
    				def DATA_SCOPE="scope=https://D89D8E604A8E4862B5FE678A9D4C5083.integration.ocp.oraclecloud.com:443urn:opc:resource:consumer::all"
    				def DATA_GRANT_TYPE="grant_type=client_credentials"
    				def cmd = [ 'bash', '-c', "curl --location --request POST ${IDCS_TOKEN_URL} --header '$HEADER_BASIC_AUTH' --header '$HEADER_CONTENT_TYPE' --data-urlencode $DATA_SCOPE --data-urlencode $DATA_GRANT_TYPE".toString()]
    				def result= cmd.execute().text
    				def jsonSlurper= new JsonSlurper()
    				def tokenObject=jsonSlurper.parseText(result)
    				def access_token=tokenObject.access_token
	
				def OIC_DEV_URL="https://polydev-axjykgitrxav-sj.integration.ocp.oraclecloud.com/ic/api/integration"
    				def INTGR_URL=OIC_DEV_URL+"/v1/integrations"
    				def AUTH_TOKEN="Authorization: Bearer "+access_token
    				cmd = [ 'bash', '-c',"curl --location --request GET '${INTGR_URL}' --header '${AUTH_TOKEN}'".toString()]
    				result=cmd.execute().text
    				def inputFile = new File("/var/jenkins_home/jobs/Test/builds/Integrations.json")
    				inputFile.write(result)
    				def intgrsObj=new JsonSlurper().parseText(result)
    				def intgrs= intgrsObj.items.collect{ ele -> ele.id.toString() }
    				return intgrs.sort()	
				
				'''
			]
		]
	]

	return intgrList

}
