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
	downstreamParameterized {
            trigger('$JOB') {
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
    job("MNTLAB-askorkin-child${i}-build-job") {
        scm {
            github('MNT-Lab/d333l-lab', 'askorkin')
        }
        parameters {
           activeChoiceParam('BRANCH_NAME') {
              groovyScript {
                  script('''
                     def command = "git ls-remote -h https://github.com/MNT-Lab/d333l-lab.git"
                     def proc = command.execute()
                     proc.waitFor()              
                     def branches = proc.in.text.readLines().collect { 
                     it.replaceAll(/[a-z0-9]*\trefs\\/heads\\//, '') 
                     }
                  return branches ''')
              }  
           }
        }  
        steps {
            shell('chmod +x script.sh')
            shell('./script.sh > output.txt')
            shell('tar -cvf ${BRANCH_NAME}_dsl_script.tar.gz jobs.groovy output.txt script.sh') 
        }
        publishers
        { 
            archiveArtifacts('output.txt')
            archiveArtifacts('${BRANCH_NAME}_dsl_script.tar.gz')
        }
    }
}
