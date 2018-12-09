job("MNTLAB-ikazlouski-main-build-job") {
	scm {
		github('MNT-Lab/d333l-lab', 'ikazlouski')
	}
	parameters {
           choiceParam('BRANCH_NAME', ['ikazlouski', 'master'])
        }

        parameters {
	 activeChoiceReactiveParam('BUILDS_TRIGGER') {
          description('Allows user choose from multiple choices')
          choiceType('CHECKBOX')
          groovyScript {
            script('def used_jobs = ["MNTLAB-ikazlouski-child1-build-job", "MNTLAB-ikazlouski-child2-build-job", "MNTLAB-ikazlouski-child3-build-job", "MNTLAB-ikazlouski-child4-build-job"] \n return used_jobs')
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

for (i = 1; i < 5; i++)
{
job('MNTLAB-ikazlouski-child' + i + '-build-job')
	scm {
         github('MNT-Lab/d333l-lab', 'ikazlouski')
        }
        parameters {
           choiceParam('BRANCH_NAME', ['ikazlouski', 'master'])
        }
        
} 	
