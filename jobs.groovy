def gitbranch() {
    def gitURL = "https://github.com/MNT-Lab/d333l-lab.git"
    def command = "git ls-remote -h $gitURL"
    def proc = command.execute()
    proc.waitFor()              
    
    def branches = proc.in.text.readLines().collect { 
        it.replaceAll(/[a-z0-9]*\trefs\/heads\//, '') 
    }
    return branches
}



us = 'kkalesnikava'


    job("MNTLAB-$us-main-build-job") {
        scm {
            github('MNT-Lab/d333l-lab', "$us")
        }
        parameters {
            choiceParam('BRANCH_NAME', ["$us", 'master'])
            activeChoiceParam('JOB') {
                description('Select child jobs')
                choiceType('CHECKBOX')
                groovyScript {
                    script('["MNTLAB-kkalesnikava-child1-build-job", "MNTLAB-kkalesnikava-child2-build-job", "MNTLAB-kkalesnikava-child3-build-job", "MNTLAB-kkalesnikava-child4-build-job"]')
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
                        predefinedProp('BRANCH_NAME', '$BRANCH_NAME')
                }                 
               
            }
            
        }

}   
   for(i in 1..4) {
    job("MNTLAB-$us-child${i}-build-job") {
        scm {
            github('MNT-Lab/d333l-lab', "$us")
        }
        parameters {
           activeChoiceParam('Branch') {
          groovyScript {
              script('gitbranch()')
               }  
            
               
       steps {
            shell('chmod +x script.sh')
            shell('./script.sh > output.txt')
            shell('tar -cvf ${us}_dsl_script.tar.gz output.txt script.sh') 
        }
   }
} 
   }    
   } 
    }
       
