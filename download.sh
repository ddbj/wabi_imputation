#!/bin/bash
#SBATCH -p imptatn
#SBATCH -o out.%A_%a.log
#SBATCH -e error.%A_%a.log

S3_BUCKET="ncgm-genome-imputation-analysis-requested"
WORKING_DIR=$1
DOWNLOAD_PATH=$2
DATA_FILE=$3;

cd $WORKING_DIR

singularity exec \
    -B /home/tomcat-tnproj-pg/imputation/aws:/home/tomcat-tnproj-pg/.aws \
    /home/tomcat-tnproj-pg/imputation/centos7-aws.simg \
    aws s3 cp s3://${S3_BUCKET}/${DOWNLOAD_PATH} ${WORKING_DIR}/${DATA_FILE}

