job("MNTLAB-ikazlouski-main-build-job") {
	scm {
		github('MNT-Lab/d333l-lab', 'ikazlouski')
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
