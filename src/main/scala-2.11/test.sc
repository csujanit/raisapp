import scala.collection.mutable.{ArrayBuffer, ListBuffer}
val seen:List[(String,Int,Int)] = List(("10",1982,19812),("10",1982,19815))
val sees:List[(String,Int,Int)] = List(("10",1982,19183),("10",1982,19812))
val myoutput = sees.map{ls => ls._2}
val myoutput2 = seen.map{ls => ls._2}


seen.sortBy(ls => (ls._1,ls._2,ls._3 ))

val s = ListBuffer[(Map[Int,ArrayBuffer[Int]])]()

s.flatMap(ls => ls.seq)



