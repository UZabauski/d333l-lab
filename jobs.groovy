job('MNTLAB-umuraveika-main-build-job') {
    scm {
        git('https://github.com/MNT-Lab/d333l-lab.git', '$Branch_name')
        }
        parameters {
                choiceParam('Branch_name', ['umuraveika', 'master'])
                activeChoiceParam('child_jobs') {
                    description('Allows user choose child jobs')
                    choiceType('CHECKBOX')
                    groovyScript {
                        script('List<String> list = new ArrayList<String>(); for (i = 1; i <5; i++) { list.add("MNTLAB-umuraveika-child$i-build-job") } ; return list')
                    }
                }
        }
    steps {
        downstreamParameterized {
            trigger('$child_jobs') { 
                parameters {
                    predefinedProp('Branch_name', '$Branch_name');
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
    job ("MNTLAB-umuraveika-child$i-build-job") {
        scm {
            github('MNT-Lab/d333l-lab', '$Branch_name')
              }
        parameters {
            choiceParam('Branch_name', ['umuraveika', 'master'])
            }
        steps {
            shell('chmod +x script.sh')
            shell('./script.sh > output.txt')
            shell('tar -czf ${Branch_name}_dsl_script.tar.gz jobs.groovy script.sh')
        }
        publishers { 
            archiveArtifacts('output.txt')
            archiveArtifacts('${Branch_name}_dsl_script.tar.gz')
        }
    }
}