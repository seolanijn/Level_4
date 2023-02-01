using System.Xml;           // XmlDocument (DOM) class
using System.Xml.XPath;     // XPathNavigator (XPath) class

namespace GlobalEconomies
{
    internal class Program
    {
        const string XmlFile = "global_economies.xml";

        static void Main(string[] args)
        {
            int startYear = 1980;
            int endYear = 1983;

            char userInput;

            try
            {
                // Load XML data from a the XML file into the DOM
                XmlDocument doc = new XmlDocument();
                doc.Load(XmlFile);

                do
                {
                    // displays a main menu
                    PrintUsage(startYear, endYear);

                    Console.Write("Your selection: ");
                    userInput = char.ToUpper(Console.ReadKey().KeyChar);

                    switch (userInput)
                    {
                        case 'Y':
                            startYear = AdjustStartingYears();
                            endYear = AdjustEndingYears(startYear);
                            break;
                        case 'R':
                            PrintRegionalSummary(doc, startYear, endYear);
                            break;
                        case 'M':
                            PrintSpecificMetric(doc, startYear, endYear);
                            break;
                        default:
                            Console.WriteLine();
                            if (userInput != 'X')
                            {
                                Console.WriteLine();
                                Console.Write("ERROR: There is no such type '" + userInput + "'. Try Again.");
                                Console.WriteLine();
                            }
                            break;
                    } // end switch
                } while (userInput != 'X');

                Console.WriteLine();
                Console.WriteLine("Exit the program.");
            }
            catch (XmlException ex)
            {
                Console.WriteLine($"\nXML ERROR: {ex.Message}");
            }
            catch (XPathException ex)
            {
                Console.WriteLine($"\nXPATH ERROR: {ex.Message}");
            }
            catch (IOException ex)
            {
                Console.WriteLine($"\nIO ERROR: {ex.Message}");
            }
            catch (Exception ex)
            {
                Console.WriteLine($"\nERROR: {ex.Message}");
            }
        } // end main

        private static void PrintSpecificMetric(XmlDocument doc, int startYid, int endYid)
        {
            XPathNavigator nav = doc.CreateNavigator()!;
            int numRegions = Convert.ToInt32(nav.Evaluate("count(//region)"));

            string[] attrName = new string[3]
            {
                "inflation",
                "interest",
                "unemployment"
            };

            // populate userSelectionList, metricList
            List<string> userSelectionList = new List<string>();
            List<string> metricList = new List<string>();
            foreach (string an in attrName)
            {
                XmlNodeList nodeList = doc.SelectNodes($"//labels/{an}/@*")!;
                foreach (XmlNode node in nodeList)
                {
                    userSelectionList.Add(node.Name);
                    metricList.Add(node.Value!);
                } // end inner foreach
            } // end outer foreach

            Console.WriteLine();

            int metricNum;
            while (true) 
            { 
                Console.WriteLine();
                Console.WriteLine("Select a metric by number as shown below...");
                // print metric list
                int idx = 0;
                foreach (string mtr in metricList)
                    Console.WriteLine($"{++idx}. {mtr}");

                Console.WriteLine();
                Console.Write("Enter a metric #: ");
                metricNum = Convert.ToInt32(Console.ReadLine()) - 1;
                if (metricNum >= 0 && metricNum <= 6)
                    break;
                else
                    Console.WriteLine("ERROR: Invalid input. Try Again.");
            } // end while

            string userSelection = findByIndex(userSelectionList, metricNum);
            string metric = findByIndex(metricList, metricNum);

            // print report
            Console.WriteLine();
            Console.WriteLine($"{metric} By Region");
            Console.WriteLine("--------------------------------");
            Console.WriteLine();
            Console.Write($"{"Region".PadLeft(60)}");

            for (int year = startYid; year <= endYid; year++)
                Console.Write(Convert.ToString(year).PadLeft(9));
            Console.WriteLine();
            Console.WriteLine();
            for (int idxRegion = 1; idxRegion <= numRegions; idxRegion++)
            {
                string info = RetrieveMetricInfo(doc, idxRegion, userSelection, startYid, endYid);
                Console.WriteLine(info.PadLeft(9));
            }
        } // end PrintSpecificMetric

        private static string RetrieveMetricInfo(XmlDocument doc, int regionNum, string attrName, int startYid, int endYid)
        {
            XmlNode regionName = doc.SelectSingleNode($"//region[{regionNum}]/@rname")!;
            string metricInfo = regionName.InnerText.PadLeft(60);

            // No line exceeds 100 characters in length
            int charInLength = regionName.InnerText.Length + ((endYid - startYid + 1) * 5);
            if (charInLength > 100)
            {
                int numCharTruc = charInLength - 100;
                metricInfo = regionName.InnerText.Substring(0, regionName.InnerText.Length - numCharTruc).PadLeft(60);
            }

            // get metric information
            for (int year = startYid; year <= endYid; year++)
            {
                XmlNode xmlNode = doc.SelectSingleNode($"//region[{regionNum}]//year[@yid={year}]//@{attrName}")!;
                if (xmlNode == null)
                    metricInfo += "-".PadLeft(9);
                else
                {
                    metricInfo += String.Format("{0:0.00}", Convert.ToDecimal(xmlNode.Value)).PadLeft(9); // round to 2 decimal
                }
            }// end for
            return metricInfo;
        } // end RetrieveMetricInfo

        private static void PrintRegionalSummary(XmlDocument doc, int startYid, int endYid)
        {
            string[] attrName = new string[4]
            {
                "year",
                "inflation",
                "interest",
                "unemployment"
            };

            // populate attrList, descList
            List<string> attrList = new List<string>();
            List<string> descList = new List<string>();
            foreach (string an in attrName)
            {
                XmlNodeList nodeList = doc.SelectNodes($"//labels/{an}/@*")!;
                foreach (XmlNode node in nodeList)
                {
                    attrList.Add(node.Name);
                    descList.Add(node.Value!);
                } // end inner
            } // end outer

            List<string> regions = new List<string>();

            // populate regions List
            XmlNodeList nodes = doc.SelectNodes("//region/@rname")!;
            foreach (XmlAttribute node in nodes)
            {
                regions.Add(node.Value);
            }
            Console.WriteLine();
            Console.WriteLine();
            Console.WriteLine("Select a region by number as shown below...");

            int numRegion;
            while (true)
            {
                int idx = 0;
                // print all regions
                foreach (string region in regions)
                    Console.WriteLine($"{++idx}. {region}");
                Console.WriteLine();
                Console.Write("Enter a region #: ");
                numRegion = Convert.ToInt32(Console.ReadLine());
                if (numRegion >= 1 && numRegion <= idx)
                    break;
                else
                    Console.WriteLine("ERROR: Invalid input. Try Again.");
                Console.WriteLine();
            }


            List<string> selectedRegionInfo = new List<string>();
            // populate selectedRegionInfo List
            nodes = doc.SelectNodes($"//region[{numRegion}]//year[@yid>={startYid} and @yid<={endYid}]")!;
            foreach (XmlElement node in nodes)
            {
                selectedRegionInfo.Add(node.OuterXml);
            }

            // extract regionName
            string regionName = doc.SelectSingleNode($"//region[{numRegion}]/@rname")!.InnerText;


            Console.WriteLine($"Economic Information for {regionName}");
            Console.WriteLine("-------------------------" + new string('-', regionName.Length));
            Console.WriteLine();

            // print report
            int j = 0;
            foreach (string attr in attrList)
            {
                string currAttr = RetrieveRegionalInfo(doc, numRegion, attr, startYid, endYid);
                string currDesc = findByIndex(descList, j).PadLeft(21);
                if (j == 0)
                {
                    Console.WriteLine($"{"Economic Metric".PadLeft(21)}{currAttr}");
                    Console.WriteLine();
                }
                else
                    Console.WriteLine($"{currDesc}{currAttr}");
                j++;
            }
        } // end PrintRegionalSummary

        private static string RetrieveRegionalInfo(XmlDocument doc, int regionNum, string attrName, int startYid, int endYid)
        {
            string regionInfo = "";
            for (int year = startYid; year <= endYid; year++)
            {
                XmlNode xmlNode = doc.SelectSingleNode($"//region[{regionNum}]//year[@yid={year}]//@{attrName}")!;
                if (xmlNode == null)
                    regionInfo += "-".PadLeft(9);
                else
                {
                    if (attrName == "yid")
                        regionInfo += xmlNode.Value!.PadLeft(9);
                    else
                        regionInfo += String.Format("{0:0.00}", Convert.ToDecimal(xmlNode.Value)).PadLeft(9); // round to 2 decimal
                }
            } // end for
            return regionInfo;
        } // end RetrieveRegionalInfo

        private static int AdjustStartingYears()
        {
            Console.WriteLine();
            Console.WriteLine();
            bool isValid = true;
            int startYear;
            do
            {
                Console.Write("Starting year (1970 to 2021): ");
                startYear = Convert.ToInt32(Console.ReadLine());
                if (startYear < 1970 || startYear > 2021) // error
                {
                    Console.WriteLine($"ERROR: Starting year must be an integer between 1970 and 2021");
                    Console.WriteLine();
                    isValid = false;
                }
                else
                    isValid = true;
            } while (!isValid);
            return startYear;
        } // end AdjustStartingYears

        private static int AdjustEndingYears(int startYid)
        {
            Console.WriteLine();
            bool isValid = true;
            int endYear;
            do
            {
                Console.Write("Ending year (1970 to 2021): ");
                endYear = Convert.ToInt32(Console.ReadLine());
                if ((endYear - startYid) >= 5 || endYear < startYid || endYear > 2021) // error
                {
                    Console.WriteLine($"ERROR: Ending year must be an integer between {startYid} and {startYid+4}");
                    Console.WriteLine();
                    isValid = false;
                }
                else
                    isValid = true;
            } while (!isValid);

            return endYear;
        } // end AdjustEndingYears

        private static string findByIndex(List<string> strList, int idx)
        {
            int currIdx = 0;
            foreach (string str in strList)
            {
                if (currIdx == idx)
                {
                    return str;
                }
                currIdx++;
            }
            return "";
        } // end findByIndex

        private static void PrintUsage(int startYid, int endYid)
        {
            Console.WriteLine();
            Console.WriteLine("World Economic Data");
            Console.WriteLine("===================");
            Console.WriteLine();
            Console.WriteLine("\'Y\' to adjust the range of year (currently " + startYid + " to " + endYid + ")");
            Console.WriteLine("\'R\' to print a regional summary");
            Console.WriteLine("\'M\' to print a specific metric for all regions");
            Console.WriteLine("\'X\' to exit the program");
        } // end PrintUsage
    }
}