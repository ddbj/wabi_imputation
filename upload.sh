#!/bin/bash
#SBATCH -p imptatn
#SBATCH -o out.%A_%a.log
#SBATCH -e error.%A_%a.log

S3_BUCKET="ncgm-genome-imputation-analysis-completed"
WORKING_DIR=$1
BASE_NAME=$2
WABI_OUT_FILE=$3
UPLOAD_PATH=$4

cd $WORKING_DIR

#データファイルを固める
tar -zcvf $WABI_OUT_FILE \
${BASE_NAME}.imputed.chr*.vcf.gz \
${BASE_NAME}.imputed.chr*.log \
error.*.log \
out.*.log

# Amazon S3にアップロード
singularity exec \
    -B /home/tomcat-tnproj-pg/imputation/aws:/home/tomcat-tnproj-pg/.aws \
    /home/tomcat-tnproj-pg/imputation/centos7-aws.simg \
    aws s3 cp ${WORKING_DIR}/${WABI_OUT_FILE} s3://${S3_BUCKET}/${UPLOAD_PATH}

