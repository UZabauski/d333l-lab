job('MNTLAB-aisachanka-main-build-job') {
	scm {
		git('https://github.com/MNT-Lab/d333l-lab.git')
	}
	parameters {
		choiceParam('BRANCH_NAME', ['aisachanka', 'master'])
		activeChoiceParam('child-jobs') {
        	    description('Allows user choose child jobs')
	            choiceType('CHECKBOX')
	            groovyScript {
	                script('List<String> list = new ArrayList<String>(); for (i = 1; i <5; i++) { list.add("MNTLAB-aisachanka-child$i-build-job") } ; return list')
	            }
	        }
	}
}

