job("MNTLAB-askorkin-main-build-job") {

scm {
        github('MNT-Lab/d333l-lab', 'askorkin')
    }
  
parameters {
	choiceParam('BRANCH_NAME', ['askorkin', 'master'], 'Select appropriate branch')
        activeChoiceParam('JOB') {
            description('Select jobs')
            choiceType('CHECKBOX')
            groovyScript {
                script('["MNTLAB-askorkin-child1-build-job", "MNTLAB-askorkin-child2-build-job", "MNTLAB-askorkin-child3-build-job", "MNTLAB-askorkin-child4-build-job"]')
            }
        }
}
        

steps {

}

}
