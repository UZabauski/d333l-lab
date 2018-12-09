job("MNTLAB-ikazlouski-main-build-job") {
	scm {
		github('MNT-Lab/d333l-lab', 'ikazlouski')
	}
	parameters {
           choiceParam('BRANCH_NAME', ['ikazlouski', 'master'])
        }

        parameters {
	 activeChoiceParam('BUILDS_TRIGGER') {
          description('Allows user choose from multiple choices')
          choiceType('CHECKBOX')
          groovyScript {
            script('["MNTLAB-ikazlouski-child1-build-job", "MNTLAB-ikazlouski-child2-build-job", "MNTLAB-ikazlouski-child3-build-job", "MNTLAB-ikazlouski-child4-build-job"]')
          }
	 }
       }
	steps
        {
            downstreamParameterized
            {
                trigger('$BUILDS_TRIGGER') 
                {
			
                    parameters
                    {
                        predefinedProp('BRANCH_NAME', '$BRANCH_NAME');
                    }
		    block 
		    {
	                buildStepFailure('FAILURE');
			failure('FAILURE');
			unstable('UNSTABLE');
		    }
                }
            }
        } 
}

for (i=1; i<5; i++)
{
job('MNTLAB-ikazlouski-child' + i + '-build-job'){
	scm {
         github('MNT-Lab/d333l-lab', 'ikazlouski')
        }
        parameters {
           choiceParam('BRANCH_NAME', ['ikazlouski', 'master'])
        }
 	steps
        {
           shell('chmod +x script.sh')
           shell('./script.sh > output.txt')
           shell('tar -czf ${BRANCH_NAME}_dsl_script.tar.gz jobs.groovy script.sh')
        }

        publishers
        { 
            archiveArtifacts('output.txt')
            archiveArtifacts('${BRANCH_NAME}_dsl_script.tar.gz')
	}
 }       
} 	
