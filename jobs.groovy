job("MNTLAB-uzabauski-main-build-job") {
	scm {
		github('https://github.com/MNT-Lab/d333l-lab.git', 'uzabauski')
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
