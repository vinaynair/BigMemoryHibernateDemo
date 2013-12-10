BigMemoryHibernateDemo
======================


Setup terracotta server
========================
See instructions to setup TSA @ https://vinaycn.wordpress.com/2013/12/09/11-steps-to-setup-bigmemory-max-platform-with-connected-clients/


How to build the sample
=======================
Project uses maven, so perform
$> mvn clean compile


How to run simple ehcache clients - PUT and GET
===============================================
#1. Put terracotta-license.key (evaluation key from the site) in the root folder of the project

#2. Start the PUT sample that pushes key,value pair to the clustered/distributed cache named cacheOne 
(Note that the sample expects key-value as comma separate strings) 
	$> mvn exec:exec -Dapp=demo.terracotta.hibernate.ehcache.Put
	.....
	Enter key,value to be put in the cache
	1,a
	Put complete
	Enter key,value to be put in the cache
	2,b

#3. Start the GET sample that gets the value given the key from the same clustered/distributed cache
(Note that the sample expects just the key)
	$>mvn exec:exec -Dapp=demo.terracotta.hibernate.ehcache.Get
	...
	Enter key to be fetched from cache
	1
	Got value=a
	Enter key to be fetched from cache
	2
	Got value=b
	Enter key to be fetched from cache


JPA setup instructions
========================

Setup derby database
====================
#1. Download opensource apache derby database from http://db.apache.org/derby/releases/release-10.10.1.1.cgi

#2. Unzip and start derby network database :-
$> [DERBY_INSTALL_FOLDER]/db-derby-10.10.1.1-bin/bin/sh startNetworkServer &

This will start a derby database server listening in default port of 1527


How to run simple JPA/Hibernate sample
=======================================

#1. Start one instance of the client that uses JPA to interact with the database & accepts a few commands
$> mvn exec:exec -Dapp=demo.terracotta.hibernate.jpa.app.Main
...

Issue the following commands to try out the cache behavior:-
clean // setup the database
addToCart joe iPhone  // adds iPhone to joe's cart
addToCart joe iPad    // adds iPad to joe's cart
addToCart joe ipod
getAllOrders joe // will retrieve all the orders from joe's cart
getAllOrdersByUserID 1 // will retrieve all orders from joe's cart using DB ID

(note that all of these will make DB queries but will update the 2nd level cache for the other clients benefit)



#2. Start another instance of the same client and issue the following command to see no SQL query being fired up
getAllOrders joe // should retrieve all data from the 2nd level , we should not be seeing any  DB queries being fired
setOrderStatus 1 in-transit // update order status, should have made an update DB query 

#3. Now go to the first instance and issue the following command 
getAllOrders joe // you should see the  updated order  & there will be no DB query fired


#4. Here are some of the other optional commands I had coded to complete the sample that we can use too:-
finalizeOrder joe 1 // to add order with identity 1 to the joe's account
getAllOrdersForAccount 1 // to get all orders that attached to the joe's account
setOrderStatus 1 shipped // change the order status, this change will be reflected on all instances

	if you go back to the other instance and issue the following command you will see the updated order
getAllOrders joe // will get show the updated order
getAllOrdersForAccount 1 // will show the update order













