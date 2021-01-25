// We now import the connection object we exported in db.js.
const db = require("../controllers/db");

// More libraries…
const express = require("express");
const bodyParser = require("body-parser");

const router = express.Router();

router.use(bodyParser.json()); // Automatically parse all POSTs as JSON.
router.use(bodyParser.urlencoded({ extended: true })); // Automatically parse URL parameters

// Skeleton for POST request
router.post("/sendsms", function (req, res) {
    let body= req.body;
    let phone = body.phone;
    let bb = body.smsbody;
    let sql = `INSERT INTO smsQ (phone, body) VALUES ("` +phone+ '", "' +bb+ '")';
    db.query(sql, function (err, result) {
        console.log("Result: " + JSON.stringify(sql));
        if (err) {
            res.send(err);
        } else {
           console.log("done");
           res.send("done sendsms");
        }
    });
});



router.post("/sent", function (req, res) {
    let body= req.body;
    let i = body.uid;
    let sql = `UPDATE smsQ SET sentflag = 1 WHERE id = ` + i;
    db.query(sql, function (err, result) {
        console.log("Result: " + JSON.stringify(sql));
        if (err) {
            res.send(err);
        } else {
            console.log("done");
            res.send("done changed flag");
        }
    });
});
// Skeleton for GET Request
router.get("/smsget", function (req, res) {
    let sql = `SELECT id,phone, body FROM smsQ WHERE sentflag = 0 ORDER BY ts ASC LIMIT 1`;
    db.query(sql, function (err, result) {
        console.log("Result: " + JSON.stringify(result));
        if (err) {
            res.send(err);
        } else {
            let obj={result};
               return res.send(obj);
        }
    });
});

// Hello World
router.get("/health", function (req, res) {
    return res.send("ok");
});
// Export the created router
module.exports = router;
