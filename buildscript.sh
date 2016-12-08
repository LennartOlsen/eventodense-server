#! /bin/bash
echo "======================== MVN BUILD ======================="
# Set current directory as working directory
cd "${0%/*}"

# rebuild old service
mvn assembly:assembly -DdescriptorId=jar-with-dependencies

echo "========== Compressing needed files to runball ========="
tar zcf runball.tar.gz target
scp runball.tar.gz lo@dvalinn:~/app/
scp runscript.sh lo@dvalinn:~/
ssh lo@dvalinn
