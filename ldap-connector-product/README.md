# Active Directory Connector

**Active Directory (AD)** is a directory service developed by Microsoft for Windows domain networks, which provides centralized management and authentication of users, computers, and other resources within the network. It uses the **Lightweight Directory Access Protocol (LDAP)** as its underlying protocol to query and manage directory information. LDAP is the standard protocol that AD employs to authenticate and authorize users and resources within a network.

Axon Ivy's **Active Directory** connector helps you accelerate process automation initiatives by querying and writing Active Directory objects in your business process. This connector:

- Allows you to easily query your Active Directory entries
- Gives you the possibility to modify attributes of Active Directory objects
- Enables the creation and addition of new Active Directory entries
- Lets you delete Active Directory objects
- Supports you with a demo implementation to reduce your integration effort.

## Demo
### Query Demo
![Active Directory Connector Demo 1](images/screen1.png "Active Directory Connector Demo 1")
![Active Directory Connector Demo 2](images/screen2.png "Active Directory Connector Demo 2")
![Active Directory Connector Demo 3](images/screen3.png "Active Directory Connector Demo 3")

### Modify Demo
![Active Directory Connector Demo 4](images/screen4.png "Active Directory Connector Demo 4")
![Active Directory Connector Demo 5](images/screen5.png "Active Directory Connector Demo 5")

## Setup

### Setting Up an Active Directory Instance
- If an existing Active Directory instance is unavailable, you can quickly set up a new instance using a Docker container. A sample Docker Compose file is provided at the following path: `ldap-connector-demo/docker/docker-compose.yaml`. This setup is intended for demonstration and testing purposes. To start the container, provide the admin password config in `ldap-connector-demo/docker/docker-compose.yaml` and execute the command:

```
docker-compose up -d
```

- If docker is not available in your local machine, an online LDAP test server can be utilize as a read-only instance
[Forumsys Online LDAP Test Server](https://www.forumsys.com/2022/05/10/online-ldap-test-server/)

**Update Active Directory connection variable**
In order to use this product you must configure multiple variables.
Add the following block to your `config/variables.yaml file` of our main Business Project that will make use of this product:

```
@variables.yaml@
```