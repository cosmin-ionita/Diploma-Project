#!/bin/bash
                                                    
echo "[Script] Starting parser..."         
java -jar logParser/Diploma-Project.jar --watch-directory=logGenerator/archiveWorkDir --destination-directory=../launchDirectory/  &
                              
sleep 1                       
                              
echo "[Script] Starting generator..."                              
for file in logGenerator/rawLogFiles/*.txt; do                 
	java -jar logGenerator/Log_Generator.jar --input-file=$file --destination-directory=logGenerator/archiveWorkDir/                   
done                        
