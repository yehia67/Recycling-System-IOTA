'use strict';
const giveReward = require('./iota_comm_modules/giveReward')
const testMam = require('./iota_comm_modules/testMam')
const manageClients = require('./iota_comm_modules/manageClients')
exports.init = async (req,res) =>{
  const response = await manageClients.init()
  res.json('DB initialized with some clients with root '+ response )
}
exports.addClient = async (req,res) =>{
  const response = await manageClients.addNewClient(req.body.root,req.body.products)
  res.json("added new client "+ response)
}
exports.addOwner = async (req,res) =>{
  const response = await manageClients.addNewOwner(req.body.root,req.body.productAddress,req.body.ownerAddress)
  res.json("added new owner  "+ response)
}
exports.giveReward = async (req,res) =>{
  const response = await giveReward.execute(req.body.root,req.body.productAddress)
  res.json(response)
}

exports.send = async (req,res) =>{
  console.log(req.body.data)
  const root = await testMam.send(req.body.data)
  res.json(root)
}

exports.fetch = async (req,res) =>{
  console.log(req.body.root)
  const result = await testMam.fetch(req.body.root)
  res.json(result)
}

exports.test = async (req,res) =>{
  res.json("API Connected")
 }
