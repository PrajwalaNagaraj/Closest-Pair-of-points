
import java.util.*;

class Point{
	public int x;
	public int y;
	public Point(int x,int y){
		this.x = x;
		this.y = y;
	}
}

public class Main
{
static int dist(Point p1, Point p2) 
{ 
    return ( (p1.x - p2.x)*(p1.x - p2.x) + 
                 (p1.y - p2.y)*(p1.y - p2.y) 
               ); 
}

static int bruteForce(Point P[], int n) 
{ 
    int min = Integer.MAX_VALUE;
    for (int i = 0; i < n; ++i) 
        for (int j = i+1; j < n; ++j) 
            if (dist(P[i], P[j]) < min) 
                min = dist(P[i], P[j]); 
    return min; 
} 
    
static int stripClosest(Point strip[], int size, int d) 
{ 
    int min = d;  // Initialize the minimum distance as d 
  
    // Pick all points one by one and try the next points till the difference 
    // between y coordinates is smaller than d
    for (int i = 0; i < size; ++i) 
        for (int j = i+1; j < size && (strip[j].y - strip[i].y) < min; ++j) 
            if (dist(strip[i],strip[j]) < min) 
                min = dist(strip[i], strip[j]); 
  
    return min; 
} 
static int closestUtil(Point Px[], Point Py[], int n) 
{ 
    // If there are 2 or 3 points, then use brute force 
    if (n <= 3) 
        return bruteForce(Px, n); 
  
    // Find the middle point 
    int mid = n/2; 
    Point midPoint = Px[mid]; 
  
  
    // Divide points in y sorted array around the vertical line. 
    // Assumption: All x coordinates are distinct. 
    Point Pyl[] = new Point[mid];   // y sorted points on left of vertical line 
    Point Pyr[] = new Point[n-mid];  // y sorted points on right of vertical line 
    int li = 0, ri = 0;  // indexes of left and right subarrays 
    for (int i = 0; i < n; i++) 
    { 
      if (Py[i].x <= midPoint.x && li<mid) 
         Pyl[li++] = Py[i]; 
      else
         Pyr[ri++] = Py[i]; 
    } 
  
    // Consider the vertical line passing through the middle point 
    // calculate the smallest distance dl on left of middle point and 
    // dr on right side 
    int dl = closestUtil(Px, Pyl, mid); 
    Point[] tmp = new Point[n-mid];
    for(int i = mid; i < n; i++) {
        tmp[i] = Px[i];
    }
    int dr = closestUtil(tmp, Pyr, n-mid); 
  
    // Find the smaller of two distances 
    int d = (dl < dr)? dl:dr; 
  
    // Build an array strip[] that contains points close (closer than d) 
    // to the line passing through the middle point 
    Point strip[] = new Point[n]; 
    int j = 0; 
    for (int i = 0; i < n; i++) {
        if (Math.abs(Py[i].x - midPoint.x) < d) {
            strip[j] = Py[i];
            j++; 
        }
    }
  
    // Find the closest points in strip.  Return the minimum of d and closest 
    // distance is strip[] 
    return stripClosest(strip, j, d); 
} 

static int closest(Point P[], int n) 
{ 
    Point Px[] = new Point[n]; 
    Point Py[] = new Point[n]; 
    for (int i = 0; i < n; i++) 
    { 
        Px[i] = P[i]; 
        Py[i] = P[i]; 
    } 
  
    Arrays.sort(Px, (a, b) -> a.x - b.x); 
    Arrays.sort(Py, (a, b) -> a.y - b.y); 
  
    // Use recursive function closestUtil() to find the smallest distance 
    return closestUtil(Px, Py, n); 
} 

	public static void main(String[] args) {
	  //Assumption
		Point[] p = new Point[3];
        p[0] = new Point(0, 0);
        p[1] = new Point(1, 1);
        p[2] = new Point(2, 2);
        int n = p.length;
        System.out.println("Dist between Closest two points: "+closest(p,n));
	}
}
