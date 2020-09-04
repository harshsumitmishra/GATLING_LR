package starterpackage

//import

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

// Run using terminal - mvn gatling:test -Dgatling.simulationClass=starterpackage.Ledger_Reporting_QA !!

class lr_qa extends Simulation {

  //Create http protocol configuration
  val httpProtocol = http

    .baseUrl("https://ledger-reporting-qa-iad.saas.pitneycloud.com")
    .header("authorization", "Basic UUE6SGpSUUZmY3BqN3FHSmpwNw==")

  val QA_DevId = "25876125"
  val QA_MerchantId = "9025876126"
  val QA_DevId_APV = "24425493"
  val QA_SBR_DevId = "24458850"

  //Create Scenario which will send http request in QA.


  val scn = scenario("QA-GetReport")

    //Prime Requests

    .exec(http("Dev_Report").get("/api/v4/reports/developers/" + QA_DevId + "/transactions"))
    .exec(http("MerchantID").get("/api/v4/reports/developers/" + QA_DevId + "/transactions?merchantId=9025876126"))
    .exec(http("TrackingNumber").get("/api/v4/reports/9470109898644518193868/transactions"))
    .exec(http("TransID").get("/api/v4/reports/developers/" + QA_DevId + "/transactions?transactionId=25876125_1392153908278558357969"))


    // Scenario will send http request for Year 2019 with Date, Size and Page Param.
    .exec(http("From-To-Date-2019").get("/api/v4/reports/developers/" + QA_DevId + "/transactions?fromDate=2019-01-01T00:00:00.000Z&toDate=2019-03-31T23:59:59.999Z"))
    .exec(http("Size and Page").get("/api/v4/reports/developers/" + QA_DevId + "/transactions?fromDate=2019-10-01T00:00:00.000Z&toDate=2019-12-31T23:59:59.999Z&size=50&page=15"))
    .exec(http("Sorting-Desc").get("/api/v4/reports/developers/" + QA_DevId + "/transactions?sort=transactionDateTime,desc&fromDate=2019-10-01T00:00:00.000Z&toDate=2019-12-31T23:59:59.999Z&size=50&page=15"))
    .exec(http("Sorting-Asc").get("/api/v4/reports/developers/" + QA_DevId + "/transactions?sort=transactionDateTime,asc&fromDate=2019-10-01T00:00:00.000Z&toDate=2019-12-31T23:59:59.999Z&size=50&page=15"))

    // Scenario will send http request for Transaction Type
    .exec(http("POSTAGE PRINT").get("/api/v4/reports/developers/" + QA_DevId + "/transactions?size=10&fromDate=2019-10-01T00:00:00.000Z&toDate=2019-12-31T23:59:59.999Z&transactionType=POSTAGE PRINT&page=0&sort=transactionDateTime,desc&merchantId=9025876126"))
    .exec(http("POSTAGE FUND").get("/api/v4/reports/developers/" + QA_DevId + "/transactions?transactionType=POSTAGE FUND&merchantId=3000001694&fromDate=2019-01-01T00:00:00.000Z&toDate=2019-12-31T23:59:59.999Z"))
    .exec(http("POSTAGE REFUND").get("/api/v4/reports/developers/" + QA_DevId + "/transactions?transactionType=POSTAGE REFUND&merchantId=9025876126&fromDate=2019-07-01T00:00:00.000Z&toDate=2019-09-30T23:59:59.999Z"))

    // Scenario will send http request for SBR
    .exec(http("SBR-ALL").get("/api/v4/reports/developers/" + QA_DevId + "/transactions?printStatus=SBR&fromDate=2019-10-01T00:00:00.000Z&toDate=2019-12-31T23:59:59.999Z&size=2000"))
    .exec(http("SBR_Printed").get("/api/v4/reports/developers/" + QA_DevId + "/transactions?printStatus=SBRPrinted&fromDate=2019-10-01T00:00:00.000Z&toDate=2019-12-31T23:59:59.999Z&size=2000"))
    .exec(http("SBR_Charged").get("/api/v4/reports/developers/" + QA_DevId + "/transactions?printStatus=SBRCharged&fromDate=2019-10-01T00:00:00.000Z&toDate=2019-12-31T23:59:59.999Z&size=2000"))

    // Scenario will send http request for Adjustments
    .exec(http("APV-POSTAGE ALL").get("/api/v4/reports/developers/" + QA_DevId_APV + "/transactions?fromDate=2019-06-01T00:00:00.000Z&toDate=2019-09-01T23:59:59.999Z&transactionType=APV-POSTAGE ALL"))
    .exec(http("APV-UNDERPAID").get("/api/v4/reports/developers/" + QA_DevId_APV + "/transactions?transactionType=APV-POSTAGE UNDERPAID&fromDate=2019-10-01T00:00:00.000Z&toDate=2019-12-31T23:59:59.999Z&size=2000"))
    .exec(http("APV-OVERPAID").get("/api/v4/reports/developers/" + QA_DevId_APV + "/transactions?transactionType=APV-POSTAGE OVERPAID&fromDate=2019-10-01T00:00:00.000Z&toDate=2019-12-31T23:59:59.999Z&size=2000"))

    // Scenario will send http request for ShipDetails
    .exec(http("ShipDetails").get("/api/v4/reports/developers/" + QA_DevId_APV + "/transactions?fromDate=2019-10-01T00:00:00.000Z&toDate=2019-12-31T23:59:59.999Z&sort=transactionDateTime,desc&merchantId=9025876126&shipDetails=0"))
    .exec(http("Without ShipDetails").get("/api/v4/reports/developers/" + QA_DevId_APV + "/transactions?fromDate=2019-10-01T00:00:00.000Z&toDate=2019-12-31T23:59:59.999Z&sort=transactionDateTime,desc&merchantId=9025876126&shipDetails=1"))

    // Scenario will send http request for paymentMethod=ACH
    .exec(http("PaymentMethod=ACH").get("/api/v4/reports/developers/25988021/transactions?paymentMethod=ACH"))

    // Combine Filter
    .exec(http("CombineFilter").get("/api/v4/reports/developers/" + QA_DevId_APV + "/transactions?transactionType=POSTAGE FUND,POSTAGE REFUND,POSTAGE PRINT&fromDate=2019-06-01T00:00:00.000Z&toDate=2019-08-30T23:59:59.999Z&size=2000"))

    // Scenario will send http request for Date, To Date and Tracking Filter
    .exec(http("Only From Date").get("/api/v4/reports/developers/" + QA_DevId + "/transactions?fromDate=2019-03-01T00:00:00.000Z"))
    .exec(http("Only To Date").get("/api/v4/reports/developers/" + QA_DevId + "/transactions?toDate=2019-12-31T23:59:59.999Z"))
    .exec(http("Tracking-Filter").get("/api/v4/reports/developers/" + QA_DevId + "/transactions?parcelTrackingNumber=9400109205291000952631"))

    //Check Archival API response

    .exec(http("Ar-Dev_Report").get("/api/v4/archived-reports/developers/" + QA_DevId + "/transactions?fromDate=2019-03-01T00:00:00.000Z"))
    .exec(http("Ar-MerchantID").get("/api/v4/archived-reports/developers/" + QA_DevId + "/transactions?fromDate=2019-03-01T00:00:00.000Z&merchantId=9025876126"))
    .exec(http("Ar-TransId").get("/api/v4/archived-reports/developers/" + QA_DevId + "/transactions?transactionId=25876125_856534464885264"))
    .exec(http("Ar-TrackingNum").get("/api/v4/archived-reports/developers/" + QA_DevId + "/transactions?parcelTrackingNumber=9469109898644503773345"))


    // Scenario will send http request for Transaction Type
    .exec(http("Ar-POSTAGE PRINT").get("/api/v4/archived-reports/developers/" + QA_DevId + "/transactions?size=10&fromDate=2019-06-01T00:00:00.000Z&toDate=2019-08-31T23:59:59.999Z&transactionType=POSTAGE PRINT&sort=transactionDateTime,desc&merchantId=9025876126"))
    .exec(http("Ar-POSTAGE FUND").get("/api/v4/archived-reports/developers/" + QA_DevId + "/transactions?size=10&fromDate=2019-06-01T00:00:00.000Z&toDate=2019-08-31T23:59:59.999Z&transactionType=POSTAGE FUND&&merchantId=9025876126"))
    .exec(http("Ar-POSTAGE REFUND").get("/api/v4/archived-reports/developers/" + QA_DevId + "/transactions?size=10&fromDate=2019-06-01T00:00:00.000Z&toDate=2019-08-31T23:59:59.999Z&transactionType=POSTAGE REFUND&sort=transactionDateTime,desc&merchantId=9025876126"))


    // Scenario will send http request for SBR
    .exec(http("Ar-SBR-ALL").get("/api/v4/archived-reports/developers/" + QA_SBR_DevId + "/transactions?printStatus=SBR&fromDate=2019-04-01T00:00:00.000Z&toDate=2019-06-30T23:59:59.999Z"))
    .exec(http("Ar-SBRPrinted-20").get("/api/v4/archived-reports/developers/" + QA_SBR_DevId + "/transactions?printStatus=SBRPrinted&fromDate=2019-04-01T00:00:00.000Z&toDate=2019-06-30T23:59:59.999Z"))
    .exec(http("Ar-SBRCharged -20").get("/api/v4/archived-reports/developers/" + QA_SBR_DevId + "/transactions?printStatus=SBRCharged&fromDate=2019-02-01T00:00:00.000Z&toDate=2019-04-30T23:59:59.999Z"))

    // Scenario will send http request for Adjustments
    .exec(http("Ar-APV").get("/api/v4/reports/developers/" + QA_DevId_APV + "/transactions?fromDate=2019-06-01T00:00:00.000Z&toDate=2019-09-01T23:59:59.999Z&transactionType=APV-POSTAGE ALL"))
    .exec(http("Ar-APV-UNDERPAID").get("/api/v4/reports/developers/" + QA_DevId_APV + "/transactions?transactionType=APV-POSTAGE UNDERPAID&fromDate=2019-10-01T00:00:00.000Z&toDate=2019-12-31T23:59:59.999Z&size=2000"))
    .exec(http("Ar-APV-OVERPAID").get("/api/v4/reports/developers/" + QA_DevId_APV + "/transactions?transactionType=APV-POSTAGE OVERPAID&fromDate=2019-10-01T00:00:00.000Z&toDate=2019-12-31T23:59:59.999Z&size=2000"))

    // Scenario will send http request for ShipDetails
    .exec(http("Ar-ShipDetails").get("/api/v4/reports/developers/" + QA_DevId_APV + "/transactions?fromDate=2019-10-01T00:00:00.000Z&toDate=2019-12-31T23:59:59.999Z&sort=transactionDateTime,desc&merchantId=9025876126&shipDetails=0"))
    .exec(http("AR-No_ShipDetail").get("/api/v4/reports/developers/" + QA_DevId_APV + "/transactions?fromDate=2019-10-01T00:00:00.000Z&toDate=2019-12-31T23:59:59.999Z&sort=transactionDateTime,desc&merchantId=9025876126&shipDetails=1"))




    // Year - 2020 Specific Requests continue from below this block :


    // Scenario will send http request for Year 2020 with Date, Size and Page Param.

    .exec(http("From-To-Date-2020").get("/api/v4/reports/developers/" + QA_DevId + "/transactions?fromDate=2020-01-01T00:00:00.000Z&toDate=2020-03-31T23:59:59.999Z"))
    .exec(http("Size & Page-2020").get("/api/v4/reports/developers/" + QA_DevId + "/transactions?fromDate=2020-01-01T00:00:00.000Z&toDate=2020-03-31T23:59:59.999Z&size=50&page=15"))
    .exec(http("Sort Desc-2020").get("/api/v4/reports/developers/" + QA_DevId + "/transactions?sort=transactionDateTime,desc&fromDate=2020-01-01T00:00:00.000Z&toDate=2020-03-31T23:59:59.999Z&size=50&page=15"))
    .exec(http("Sort Asc -2020").get("/api/v4/reports/developers/" + QA_DevId + "/transactions?sort=transactionDateTime,asc&fromDate=2020-01-01T00:00:00.000Z&toDate=2020-03-31T23:59:59.999Z&size=50&page=15"))

    // Scenario will send http request for Transaction Type and year - 2020
    .exec(http("POSTAGE PRINT-2020").get("/api/v4/reports/developers/" + QA_DevId + "/transactions?size=10&fromDate=2020-01-01T00:00:00.000Z&toDate=2020-03-31T23:59:59.999Z&transactionType=POSTAGE PRINT&page=0&sort=transactionDateTime,desc&merchantId=9025876126"))
    .exec(http("POSTAGE FUND-2020").get("/api/v4/reports/developers/" + QA_DevId + "/transactions?transactionType=POSTAGE FUND&merchantId=3000001694&fromDate=2020-01-01T00:00:00.000Z&toDate=2020-03-31T23:59:59.999Z"))
    .exec(http("POSTAGE REFUND-2020").get("/api/v4/reports/developers/" + QA_DevId + "/transactions?transactionType=POSTAGE REFUND&merchantId=9025876126&fromDate=2020-07-01T00:00:00.000Z&toDate=2020-09-30T23:59:59.999Z"))

    // Scenario will send http request for SBR -2020
    .exec(http("SBR-ALL-2020").get("/api/v4/reports/developers/" + QA_DevId + "/transactions?printStatus=SBR&fromDate=2020-07-01T00:00:00.000Z&toDate=2020-09-30T23:59:59.999Z&size=2000"))
    .exec(http("SBRPrinted-2020").get("/api/v4/reports/developers/" + QA_DevId + "/transactions?printStatus=SBRPrinted&fromDate=2020-07-01T00:00:00.000Z&toDate=2020-09-30T23:59:59.999Z&size=2000"))
    .exec(http("SBRCharged-2020").get("/api/v4/reports/developers/" + QA_DevId + "/transactions?printStatus=SBRCharged&fromDate=2020-07-01T00:00:00.000Z&toDate=2020-09-30T23:59:59.999Z&size=2000"))

    // Scenario will send http request for Adjustments -2020
    .exec(http("APV-ALL-2020").get("/api/v4/reports/developers/" + QA_DevId + "/transactions?fromDate=2020-07-01T00:00:00.000Z&toDate=2020-09-30T23:59:59.999Z&transactionType=APV-POSTAGE ALL"))
    .exec(http("APV-UNDERPAID-2020").get("/api/v4/reports/developers/" + QA_DevId + "/transactions?transactionType=APV-POSTAGE UNDERPAID&fromDate=2020-07-01T00:00:00.000Z&toDate=2020-09-30T23:59:59.999Z&size=2000"))
    .exec(http("APV-OVERPAID-2020").get("/api/v4/reports/developers/" + QA_DevId + "/transactions?transactionType=APV-POSTAGE OVERPAID&fromDate=2020-07-01T00:00:00.000Z&toDate=2020-09-30T23:59:59.999Z&size=2000"))

    // Scenario will send http request for ShipDetails -2020
    .exec(http("ShipDetails-2020").get("/api/v4/reports/developers/" + QA_DevId_APV + "/transactions?fromDate=2020-07-01T00:00:00.000Z&toDate=2020-09-30T23:59:59.999Z&sort=transactionDateTime,desc&merchantId=9025876126&shipDetails=0"))
    .exec(http("No-ShipDetails-2020").get("/api/v4/reports/developers/" + QA_DevId_APV + "/transactions?fromDate=2020-07-01T00:00:00.000Z&toDate=2020-09-30T23:59:59.999Z&sort=transactionDateTime,desc&merchantId=9025876126&shipDetails=1"))


  //Inject Load
  setUp(scn.inject(atOnceUsers(5))).protocols(httpProtocol)


}