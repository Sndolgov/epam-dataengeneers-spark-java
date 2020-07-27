package com.epam.andrey

import com.epam.count_words.WordService

/**
 * @author Evgeny Borisov
 */
object CaseExample {
  def main(args: Array[String]): Unit = {
    processPoint(List(new Point(1, 2, 1)))
  }

  def processPoint(points: List[Point]): Unit = {
    points.filter {
      case Point(1, 2, 2) => true
      case Point(10, x, _) => x > 10
      case Point(a, b, _) => a > 100

    }
  }

  def checkPoint(point: Point): Boolean = true
}


class Point(val x: Int, val y: Int, val z: Int) extends WordService {

  def a(list: List[String]): Unit = {
    val x = list match {
      case Nil => 10
      case List("x", "y", _) => 11
      case _ => 0
    }
  }
}

object Point {

  throw new NoSuchElementException
  type FunctorType = (Int,Int)=>Boolean

  def doSomeThing(f: FunctorType):Boolean=true


  def calc(int: Int,f: FunctorType, z: Int):Unit={}



  def unapply(p: Point): Option[(Int, Int, Int)] = {
    Option(p.x, p.y, p.z)
  }
}


