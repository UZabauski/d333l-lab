job('MNTLAB-aisachanka-main-build-job') {
	scm {
		git('https://github.com/MNT-Lab/d333l-lab.git', '$BRANCH_NAME')
        }
        parameters {
                choiceParam('BRANCH_NAME', ['aisachanka', 'master'])
                activeChoiceParam('child_jobs') {
                    description('Allows user choose child jobs')
                    choiceType('CHECKBOX')
                    groovyScript {
                        script('List<String> list = new ArrayList<String>(); for (i = 1; i <5; i++) { list.add("MNTLAB-aisachanka-child$i-build-job") } ; return list')
                    }
                }
        }
	steps {
		downstreamParameterized {
			trigger('$child_jobs') { 
				parameters {
					predefinedProp('BRANCH_NAME', '$BRANCH_NAME');
                    		}
		    	block {
      				buildStepFailure('FAILURE');
				failure('FAILURE');
				unstable('UNSTABLE');
		    	}
                	}
            	}
	}
}

for (i = 1; i <5; i++) {
	job ("MNTLAB-aisachanka-child$i-build-job") {
		scm {
			github('MNT-Lab/d333l-lab', '$BRANCH_NAME')
      		}
		parameters {
	        	gitParam('BRANCH_NAME') {
	            	description('branch from git')
	            	type('BRANCH')
        		}
    		}
		steps {
	           shell('chmod +x script.sh')
	           shell('./script.sh > output.txt')
		}
    }
}
