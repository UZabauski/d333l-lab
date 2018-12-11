job("MNTLAB-uzabauski-main-build-job") {
	scm {
		github('MNT-Lab/d333l-lab', 'uzabauski')
	}
	parameters {
		choiceParam('BRANCH_NAME', ['uzabauski', 'master'], 'Select appropriate branch')
		activeChoiceParam('jobs') {
			description('Select jobs')
			choiceType('CHECKBOX')
			groovyScript {
				script('''a = []
						  for(i in 1..4) {
						  	  a.add("MNTLAB-uzabauski-child${i}-build-job" )
						  }
						  return a''')
			}
		}
	}
	steps {
		downstreamParameterized {
			trigger('$jobs') {
				block {
					buildStepFailure('FAILURE')
					failure('FAILURE')
					unstable('UNSTABLE')
				}
				parameters {
					predefinedProp('BRANCH_NAME', '${BRANCH_NAME}')
				}
			}
		}
	}
}
for(i in 1..4) {
	job("MNTLAB-uzabauski-child${i}-build-job") {
		scm {
			github('MNT-Lab/d333l-lab', '$BRANCH_NAME')
		}
		parameters {
			gitParam('BRANCH_NAME') {
			description('branch from git')
			type('BRANCH')            
		}  
        steps {
            shell('chmod +x script.sh')
            shell('./script.sh > output.txt')
            shell('tar -cvf ${BRANCH_NAME}_dsl_script.tar.gz jobs.groovy output.txt script.sh') 
        }
        publishers { 
            archiveArtifacts('output.txt')
            archiveArtifacts('${BRANCH_NAME}_dsl_script.tar.gz')
        }
	}
}
}
