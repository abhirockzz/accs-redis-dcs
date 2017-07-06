# Build, deploy and test a Redis based Java application using Oracle Cloud

Check [the blog](https://community.oracle.com/community/cloud_computing/oracle-cloud-developer-solutions/blog/2017/07/06/develop-and-cicd-a-redis-based-application-using-oracle-cloud) for details

## Build

- `git clone https://github.com/abhirockzz/accs-redis-dcs.git`
- `mvn clean install` - creates `acc-redis-dcs-dist.zip` in `target` directory

## Deploy

- Use Developer Cloud - read [the blog](https://community.oracle.com/community/cloud_computing/oracle-cloud-developer-solutions/blog/2017/07/06/develop-and-cicd-a-redis-based-application-using-oracle-cloud#jive_content_id_Configure_Oracle_Developer_Cloud)
- Use Application Container Cloud [console](http://docs.oracle.com/en/cloud/paas/app-container-cloud/csjse/exploring-application-deployments-page.html#GUID-5E4472B1-F5C6-4556-908C-D76C4C14FC60)
- Use Application Container Cloud [REST APIs](http://docs.oracle.com/en/cloud/paas/app-container-cloud/apcsr/op-paas-service-apaas-api-v1.1-apps-%7BidentityDomainId%7D-post.html)
- Use Application Container Cloud [PSM APIs](https://docs.oracle.com/en/cloud/paas/java-cloud/pscli/accs-push.html)
