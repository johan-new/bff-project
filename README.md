# Backend-Frontend Fanatics BFF
Projektarbete ht20 JAVA19 @ YRGO

### Complete setup from scratch for dev/test in Ubuntu
Setting up backend.
```
sudo apt -y install maven && \
git clone https://github.com/johan-new/bff-project.git && \
mvn install && \
java -jar target/project-0.0.1-SNAPSHOT.jar
```

Setting up frontend
```
sudo apt -y install snapd && \
sudo snap install node --classic && \
npm install vue --save && \
npm install vuex --save && \
npm install vee-validate --save && \
npm install bootstrap@3 --save && \
npm install --save animejs && \
cd bff-project/bff_frontend/  && \
npm install  && \
npm run serve
```
