import com.github.polomarcus.utils.ClimateService
import com.github.polomarcus.model.CO2Record
import org.scalatest.funsuite.AnyFunSuite

//@See https://www.scalatest.org/scaladoc/3.1.2/org/scalatest/funsuite/AnyFunSuite.html
class ClimateServiceTest extends AnyFunSuite {
  test("containsWordGlobalWarming - non climate related words should return false") {
    assert(ClimateService.isClimateRelated("pizza") == false)
  }

  test("isClimateRelated - climate related words should return true") {
    assert(ClimateService.isClimateRelated("climate change") == true)
    assert(ClimateService.isClimateRelated("IPCC"))
    assert(ClimateService.isClimateRelated("global warming"))
  }

  //@TODO
  test("parseRawData for positive ppm") {
    // our inputs
    val firstRecord = (2003, 1, 355.2)     //help: to access 2003 of this tuple, you can do firstRecord._1
    val secondRecord = (2004, 1, 375.2)
    val list1 = List(firstRecord, secondRecord)

    // our output of our method "parseRawData"
    val co2RecordWithType = CO2Record(firstRecord._1, firstRecord._2, firstRecord._3)
    val co2RecordWithType2 = CO2Record(secondRecord._1, secondRecord._2, secondRecord._3)
    val output = List(Some(co2RecordWithType), Some(co2RecordWithType2))

    // we call our function here to test our input and output
    assert(ClimateService.parseRawData(list1) == output)
  }

  test("parseRawData for negative ppm") {
    // our inputs
    val firstRecord = (2003, 1, -355.2) //help: to access 2003 of this tuple, you can do firstRecord._1
    val secondRecord = (2004, 1, 375.2)
    val list1 = List(firstRecord, secondRecord)

    // our output of our method "parseRawData"
    val co2RecordWithType2 = CO2Record(secondRecord._1, secondRecord._2, secondRecord._3)
    val output = List(None, Some(co2RecordWithType2))

    // we call our function here to test our input and output
    assert(ClimateService.parseRawData(list1) == output)
  }

  test("getMinMax"){
    // inputs
    val firstRecord = (2003, 1, 355.2)
    val secondRecord = (2004, 1, 375.2)
    val thirdRecord = (2005, 1, 420.69)
    val recordList = List(firstRecord, secondRecord, thirdRecord)
    val co2record1 = CO2Record(firstRecord._1, firstRecord._2, firstRecord._3)
    val co2record2 = CO2Record(secondRecord._1, secondRecord._2, secondRecord._3)
    val co2record3 = CO2Record(thirdRecord._1, thirdRecord._2, thirdRecord._3)
    val co2recordList = List(co2record1, co2record2, co2record3)

    // output
    val output = (355.2, 420.69)

    // test
    assert(ClimateService.getMinMax(co2recordList) == output)

  }

  test("getMinMaxByYear"){
    // inputs
    val recordList = List(
      (1958, 3, 316.19), // records from 1958
      (1958, 4, 317.29),
      (1958, 5, 317.87),
      (1972, 6, 329.56), // records from 1972
      (1972, 7, 328.18),
      (1972, 8, 326.32))
    val co2recordList = recordList.map(record => CO2Record(record._1, record._2, record._3))
    val year_1958 = 1958
    val year_1972 = 1972

    //output
    val output_for_year_1958 = (316.19, 317.87)
    val output_for_year_1972 = (326.32, 329.56)

    //tests
    assert(ClimateService.getMinMaxByYear(co2recordList, year_1958) == output_for_year_1958)
    assert(ClimateService.getMinMaxByYear(co2recordList, year_1972) == output_for_year_1972)
  }

  //@TODO
  test("filterDecemberData") {
    // inputs
    val firstRecord = (1958, 3, 316.19) // valid record
    val secondRecord = (1958, 12, 317.87) // december month
    val thirdRecord = (1958, 5, -256.4) // negative ppm
    val recordList = List(firstRecord, secondRecord, thirdRecord)
    val co2RecordList = ClimateService.parseRawData(recordList)

    // output
    val output = List(CO2Record(firstRecord._1, firstRecord._2, firstRecord._3))
    assert(ClimateService.filterDecemberData(co2RecordList) == output)
  }
}
