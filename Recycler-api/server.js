var express = require('express'),
  app = express(),
  port = process.env.PORT || 5002,
  bodyParser = require('body-parser');
  

 
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());


var routes = require('./api/routes/recyclerRoutes'); //importing route
routes(app); //register the route


app.listen(port);

app.use(function(req, res) {
  res.status(404).send({url: req.originalUrl + ' not found'})
});
console.log('recycler RESTful API server started on: ' + port);
