/*
 * Sciagnac pubsubclient wersja co najmniej 2.4
 * odpalic pubsubclient.h
 * i zmienic wersje MQTT na 3_1
 */

/*
* DHT11:
* VCC DATA  X GND
*/

/*
* IR Receiver:
* DATA GND VCC
*/

#include <SPI.h>
#include <Ethernet.h>
#include <PubSubClient.h>
#include <dht.h>
#include <IRremote.h>
dht DHT;
char message_buff[100];

//do Gobetwino
int serInLen = 25;
char serInString[25];
int pId =0;
int result;

//******************************************************************************************************//
//do pilota TV:
/*
 * Ustawienia dla pilota
Pilot Yamaha ustawiony na CD 
Wcisnac i trzymac przycisk CD i przez 3 sekundy trzymac razem z POWER AV, potem kod 2078
kod dla TV: 0069
sciagnac irremote z https://github.com/z3t0/Arduino-IRremote
*/

unsigned int irLen = 0; // The length of the code
unsigned int irBuffer[RAWBUF]; // Storage for raw (UNKNOWN) codes
int codeType; // The type of code (NEC, SONY, UNKNOWN, etc.)
unsigned int codeValue; // The value of a known code
boolean codeReceived = false; // A boolean to keep track of if a code was received

const String TOPIC_PILOT = "pilot";
const String TOPIC_LIS = "lis";
const String TOPIC_CZUJ = "czuj";

const String SUBTOPIC_AMP = "amp";
const String SUBTOPIC_TV = "tv";
const String SUBTOPIC_LCD = "lcd";
const String SUBTOPIC_SPEAK = "speak";
const String SUBTOPIC_SUB = "sub";
const String SUBTOPIC_ETC = "etc";
const String SUBTOPIC_GET = "get";

const long  AMP_VOLUP =   0x5EA158A7;
const long  AMP_VOLDN =   0x5EA1D827;
//const long  AMP_ON =      0x7E817E81;
//const long  AMP_OFF =     0x7E81FE01;
//const long  AMP_NIGHT =   0x5EA1A956;
//const long  AMP_AUDIOSRC =0x5EA1C33C;
//const long  AMP_SCENE1 =  0x5EA100FE;
//const long  AMP_SCENE2 =  0x5EA1C03E;
//const long  AMP_SCENE3 =  0x5EA1609E;
const long  AMP_SCENE4 =  0x5EA1906E;

unsigned int TV_ON[] = {4500,4450, 550,1700, 550,1650, 550,1700, 600,550, 550,600, 550,550, 600,550, 550,600, 550,1700, 550,1700, 550,1650, 550,600, 550,600, 550,600, 550,600, 550,550, 600,550, 600,1650, 550,600, 550,600, 550,600, 550,550, 600,550, 550,600, 550,1700, 550,600, 550,1650, 550,1700, 550,1700, 550,1650, 600,1650, 550,1700, 550};

/*const long  RADIO_CHNL1 = 0x5EA1A758;
const long  RADIO_CHNL2 = 0x5EA16798;
const long  RADIO_CHNL3 = 0x5EA1E718;
const long  RADIO_CHNL4 = 0x5EA117E8;
const long  RADIO_CHNL5 = 0x5EA19768;
const long  RADIO_CHNL6 = 0x5EA157A8;
const long  RADIO_CHNL7 = 0x5EA1D728;
const long  RADIO_CHNL8 = 0x5EA137C8;*/

const long  CD1 =         0x8B73AC5;
const long  CD2 =         0x8B77887;
const long  CD3 =         0x8B77A85;
const long  CD4 =         0x8B71AE5;
const long  CD5 =         0x8B758A7;
const long  CD6 =         0x8B7DA25;
const long  CD7 =         0x8B7D827;
const long  CD8 =         0x8B79A65;
const long  CD9 =         0x8B75AA5;
const long  CD0 =         0x8B718E7;


const int lis_lcd = 25;    //listwa 1
const int lis_speak = 22;    //listwa 2
const int lis_amp = 23;    //listwa 3
const int lis_sub = 24;    //listwa 4
const int lis_etc = 26;    //listwa 5
const int led_red = 7;
const int led_grn = 6;

const int but_pc = 7; 

#define DHT11_PIN A0 //czujnik temp i wilgoci na balkonie
const int IR_RECV_PIN = A2;    //czujnik IR pilota
IRrecv irrecv(IR_RECV_PIN);
decode_results results;
IRsend irsend;       //nadajniki IR pilotów, pin 9

unsigned long code;  //do pilota
// Update these with values suitable for your network.
byte mac[]    = {  0xDE, 0xED, 0xCA, 0xFB, 0xFE, 0xBA };
IPAddress ip(192, 168, 1, 104);      //adres Arduino
IPAddress server(192, 168, 1, 103);  //adres brokera

//************************************************************************************************************//

// Callback function header
void callback(char* topic, byte* payload, unsigned int length);
EthernetClient ethClient;
PubSubClient client(server, 1883, callback, ethClient);

// Callback function
void callback(char* topic, byte* payload, unsigned int length) {  //wiadomosc z brokera
  led(led_red);
  String top = topic;
  String msg = String((char*)payload);
  msg = msg.substring(0, length);
  Serial.println("Received message from broker:\t=" + msg + "\tin topic: " + top);
  
  String mainTopic = top.substring(0, top.indexOf("/"));
  String restTopic = top.substring(top.indexOf("/")+1);
  String subTopic = "";
  if(restTopic.indexOf("/") > 0)
      subTopic = restTopic.substring(0, restTopic.indexOf("/"));
  else
      subTopic = restTopic;
  
  if (mainTopic == TOPIC_LIS && !top.endsWith("status")){
      useLis(subTopic, msg);
      
      //uaktualnij status listwy
      byte* p = (byte*)malloc(length);
      // Copy the payload to the new buffer
      memcpy(p, payload, length);
      client.publish((mainTopic + "/" + subTopic + "/status").c_str(), p, length);
      free(p);
  }
  else if (mainTopic == TOPIC_PILOT){
    usePilot(subTopic, payload);
  }
  else if (mainTopic == TOPIC_CZUJ && subTopic == SUBTOPIC_GET) {
      meteo();
  }
  else if(top == "pcCMD"){
    pcCMD(topic, payload, length);
  }
  else if(top == "volume"){
    volume(topic, payload, length);
  }
}

//*******************************************************************************************************************//

void setup() {
  pinMode(lis_lcd, OUTPUT);
  pinMode(lis_speak, OUTPUT);
  pinMode(lis_amp, OUTPUT);
  pinMode(lis_sub, OUTPUT);
  pinMode(lis_etc, OUTPUT);
  pinMode(but_pc, OUTPUT);
  pinMode(led_red, OUTPUT);
  pinMode(led_grn, OUTPUT);
  
  digitalWrite(lis_lcd, HIGH);
  digitalWrite(lis_speak, HIGH);
  digitalWrite(lis_amp, HIGH);
  digitalWrite(lis_sub, HIGH);
  digitalWrite(lis_etc, HIGH);
  digitalWrite(led_red, LOW);
  digitalWrite(led_grn, LOW);

  irrecv.enableIRIn();            // Start the receiver

  Serial.begin(9600);

  Ethernet.begin(mac, ip);
  Serial.println("___");
  Serial.println("Attempting to connect to MQTT broker...");
  if (client.connect("arduinoClient")) {
    led(led_grn);
    led(led_grn);
    led(led_grn);
    client.publish("outTopic", "MEGA connected");
    client.subscribe("lis/#");  //sluchaj wszystkiego pod topikiem listwy
    client.subscribe("czuj/#");  //sluchaj wszystkiego pod topikiem czujnika ruchu
    client.subscribe("czuj");  //sluchaj wszystkiego pod topikiem czujnika ruchu
    client.subscribe("pilot/amp");  //sluchaj wszystkiego pod topikiem pilota
    client.subscribe("pilot/tv");  //sluchaj wszystkiego pod topikiem pilota
    client.subscribe("volume");  //sluchaj wszystkiego pod topikiem pilota volume
    client.subscribe("pcCMD");  //sluchaj wszystkiego pod topikiem buttons
    client.publish("pcCMD", "PCPC");
//    client.subscribe("buttons");  //sluchaj wszystkiego pod topikiem buttons
    Serial.println("Arduino MEGA successfully connected");
  }
  else {
    led(led_red);
    led(led_red);
    led(led_red);
    Serial.println("Arduino MEGA connection failed");
    Serial.println(client.state());
  }
  Serial.println("___");
}

void loop() {
  client.loop();          //do mqtt
  pilot();
}

//////////////////    listwy    //////////////////////

void useLis(String subTopic, String cmd) {
  int elemNo = 0;
       if (subTopic== SUBTOPIC_LCD) elemNo = lis_lcd;
  else if (subTopic== SUBTOPIC_SPEAK) elemNo = lis_speak;
  else if (subTopic== SUBTOPIC_AMP) elemNo = lis_amp;
  else if (subTopic== SUBTOPIC_SUB) elemNo = lis_sub;
  else if (subTopic== SUBTOPIC_ETC) elemNo = lis_etc;
  
  if (cmd == "ON" ) {
    digitalWrite(elemNo, HIGH);
  }
  else {
    digitalWrite(elemNo, LOW);
  }
}


//////////////////    pilot    //////////////////////
void usePilot(String subTopic, byte* payload) {
  if(subTopic == SUBTOPIC_AMP)
    usePilotAmp(payload);
  else if(subTopic == SUBTOPIC_TV)
    usePilotTV(payload);
}

//////////////////    pilotAmp    //////////////////////
void usePilotAmp(byte* payload) {
  long signal = strtol((char*)payload, NULL, 16);
  irsend.sendNEC(signal, 32);
  delay(40);
  irsend.sendNEC(0xFFFFFFFF, 32);
  delay(40);
  irrecv.enableIRIn();
  irrecv.resume();
}

//////////////////    pilotTV    //////////////////////
void usePilotTV(byte* payload) {
  long signal = strtol((char*)payload, NULL, 16);
  irsend.sendRaw(TV_ON, 67, 38);
  delay(100);
  irsend.sendNEC(0xFFFFFFFF, 32);
  delay(40);
  irrecv.enableIRIn();
  irrecv.resume();
}

//////////////////    volume    //////////////////////
void volume(char* topic, byte* payload, unsigned int length) {
  //topic: volume
  String el = topic;
  String cmd = "";
  for (int i=0;i<length;i++) {
    cmd = cmd +((char)payload[i]);
  }
  
  int value = cmd.toInt();
  long  VOLUME;
  if(value>=0) VOLUME = AMP_VOLUP;
  else VOLUME = AMP_VOLDN;
  for(int i=0; i<abs(value)+1; i++){
    irsend.sendNEC(VOLUME, 32);
    delay(40);
    irsend.sendNEC(0xFFFFFFFF, 32);
    delay(40);
    irrecv.enableIRIn();
    irrecv.resume();
  } 
  Serial.println("Volume changed by " + value);
}

//**************************************************************************************************************//

void meteo() {
  int chk = DHT.read11(DHT11_PIN);
  String wilg = String(DHT.humidity, 1).c_str();
  String temp = String(DHT.temperature, 1).c_str();
  Serial.println(wilg);
  switch (chk)
  {
    case DHTLIB_OK:
      Serial.print("OK,\t");
      break;
    case DHTLIB_ERROR_CHECKSUM:
      Serial.print("Checksum error,\t");
      wilg = "N/A";
      temp = "N/A";
      break;
    case DHTLIB_ERROR_TIMEOUT:
      Serial.print("Time out error, \t");
      wilg = "N / A";
      temp = "N / A";
      break;
    default:
      Serial.print("Unknown error, \t");
      wilg = "N / A";
      temp = "N / A";
      break;
  }
  Serial.print(",\t");
  Serial.print("Wilg\t" + wilg);
  Serial.print(",\t");
  Serial.println("Temp\t" + temp);
  led(led_grn);
  client.publish("czuj/wilg", wilg.c_str());   //wydruk odczytu czujnika CMD-- mosquitto_sub -h 192.168.0.103 -p 1883 -t k_czuj_1
  client.publish("czuj/temp", temp.c_str());   //wydruk odczytu czujnika CMD-- mosquitto_sub -h 192.168.0.103 -p 1883 -t k_czuj_1
}

//**************************************************************************************************************************//

void pilot(){
code=0;
if (irrecv.decode(&results)) {
code = results.value;
irrecv.resume(); // Receive the next value
if(code!=AMP_VOLUP && code!=AMP_VOLDN){
  Serial.print("Protocol: ");
  Serial.println(results.decode_type, DEC);
  Serial.println(results.value, HEX);
  led(led_grn);  
  switch (code){
    case CD1:
    client.publish("pilot/rcv", "1");
    Serial.println("pilot 1 sent");
    break;
    case CD2:
    client.publish("pilot/rcv", "2");
    Serial.println("pilot 2 sent");
    break;
    case CD3:
    client.publish("pilot/rcv", "3");
    Serial.println("pilot 3 sent");
    break;
    case CD4:
    client.publish("pilot/rcv", "4");
    Serial.println("pilot 4 sent");
    break;
    case CD5:
    client.publish("pilot/rcv", "5");
    Serial.println("pilot 5 sent");
    break;
    case CD6:
    client.publish("pilot/rcv", "6");
    Serial.println("pilot 6 sent");
    break;
    case CD7:
    client.publish("pilot/rcv", "7");
    Serial.println("pilot 7 sent");
    break;
    case CD8:
    client.publish("pilot/rcv", "8");
    Serial.println("pilot 8 sent");
    break;
    case CD9:
    client.publish("pilot/rcv", "9");
    Serial.println("pilot 9 sent");
    break;
    case CD0:
    client.publish("pilot/rcv", "0");
    Serial.println("pilot 0 sent");
    break;
    case AMP_SCENE4:
    client.publish("pilot/rcv", "10");
    Serial.println("pilot 10 sent");
    break;
  }
  delay(40);
  irsend.sendNEC(0xFFFFFFFF, 32);
  delay(40);
}
irrecv.enableIRIn();
irrecv.resume();
}
}

//**************************************************************************************************************//

//read a string from the serial and store it in an array
//you must supply the array variable - will return if timeOut ms passes before the sting is read so you should
//check the contents of the char array before making any assumptions.
void readSerialString (char *strArray,long timeOut) 
{
   long startTime=millis();
   int i;

   while (!Serial.available()) {
      if (millis()-startTime >= timeOut) {
         return;
      }
   }
   while (Serial.available() && i < serInLen) {
      strArray[i] = Serial.read();
      i++;
   }
   for (i = 0; i < sizeof(strArray) - 1; i++){
   Serial.println(strArray[i]);
   }
}

//**************************************************************************************************************//

void pcCMD(char* topic, byte* payload, unsigned int length) {
  String el = topic;
  String cmd = "";
  int button = 0;
  for (int i=0;i<length;i++) {
    cmd = cmd +((char)payload[i]);
  }
  if(cmd == "PCON") {
    digitalWrite(but_pc, HIGH);
    delay(100);
    digitalWrite(but_pc, LOW);
    Serial.println(button + " " + cmd + " pressed");
  }else if(cmd == "PCOFF") {//////////////////////////////////////////////////////////////////////////////////////
    Serial.println("#S|PCOFF|[]#");
    readSerialString(serInString, 1000);
    Serial.println("Switching the PC OFF");
  }else if(cmd == "PCPC") {
    Serial.println("#S|PCPC|[]#");
    readSerialString(serInString, 1000);
    Serial.println("Audio source selected: PC");
  }else if(cmd == "PCAMP") {
    Serial.println("#S|PCAMP|[]#");
    readSerialString(serInString, 1000);
    Serial.println("Audio source selected: AMP");
  } 
}

void led(int led_clr){
  digitalWrite(led_clr, HIGH);
  delay(10);
  digitalWrite(led_clr, LOW);
  delay(10);
}

