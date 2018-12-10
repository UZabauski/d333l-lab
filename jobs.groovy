job('MNTLAB-aisachanka-main-build-job') {
	scm {
		git('https://github.com/MNT-Lab/d333l-lab.git')
	}
	parameters {
	choiceParam('BRANCH_NAME', ['aisachanka', 'master'])
	}
}

