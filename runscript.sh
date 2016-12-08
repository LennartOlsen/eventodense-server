#! /bin/bash
# Set current directory as working directory
cd "${0%/*}"
echo "======================== REMOVE OLD BUILD ======================="
rm -rf app/target
echo "======================== UNPACK NEW BUILD ======================="
tar -xvf app/runball.tar.gz -C app


echo "======================== RUN NEW BUILD ======================="
nohup java -cp app/target/event-odense-server-0.2-jar-with-dependencies.jar net.lennartolsen.eventodense.server.Main </dev/null 2>&1 | tee logfile.log &