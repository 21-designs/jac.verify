# annotation-verification-lib
Provides different annotations that refer to the codes of conduct of the value range of declared class fields.

---

Adds several annotations to validate different class field content like the minimum value or the length of an array. Use those annotations to restrict the input possibilities similar to assertions.

Featured *class field* annotations included are listed below:

- `@Interval(max, min)`
- `@Max(max)`
- `@Min(min)`
- `@NotNull`
- `@MaxSize(maxSize)`
- `@MinSize(minSize)`

### Annotaion Examples 
```java
public class ExampleAnnotation
{
  // Limits the range
  @Interval( min = 0, max = 50 )
  int arg1 = 10;
  
  // Limits the maximum value
  @Max(50)
  int arg2 = 10;
  
  // Limits the minimum value
  @Min(0)
  int arg3 = 10;
  
  // Asserts that the value is not null
  @NotNull
  Object arg4 = new Object();
  
  // Limits different types of accumulations
  @MaxSize(3)
  Integer[] array1 = new Integer[] {1,2};
  @MaxSize(3)
  List<Integer> list1 = Arrays.asList( array );;
  @MaxSize(3)
  Set<Integer> set1 = new HashSet<Integer>(list);;
  @MaxSize(3)
  Queue<Integer> queue1 = new LinkedList<Integer>(list);
  @MaxSize(3)
  String string1 = "12";
  
  // Limits different types of accumulations
  @MinSize(3)
  Integer[] array2 = new Integer[] {1,2,3,4};
  @MinSize(3)
  List<Integer> list2 = Arrays.asList( array );;
  @MinSize(3)
  Set<Integer> set2 = new HashSet<Integer>(list);;
  @MinSize(3)
  Queue<Integer> queue2 = new LinkedList<Integer>(list);
  @MinSize(3)
  String string2 = "1234";
  
  // Combined
  @MaxSize(16)
  @NotNull
  String password = "unknown";
}
```

### Validation
An example class type containing one member restricted by an annotation.
```java
public class ExampleType
{
  @NotNull
  String s = "foo";
}
```
The following implementation shows how to verify all constraints defined by the specified annotations.
```java
public class ExampleVerification
{
  ExampleType obj1 = new ExampleType();
  ExampleType obj2 = new ExampleType();

  ExampleVerification()
  {
    // Stores the verifications of all annotations in a Result object
    Result result1 = AVerification.verify( obj1 );
    
    // Merges the results of two different objects.
    Result result2 = AVerification.verify( obj2 );
    result1.merge( result2 ) );
    
    if( result1.passedAll() )
    {
      // Be happy
    }
    else
    {
      // Review your code
    }
  }
}
```

### Evaluation
This package also includes test cases to prove reliability of the related annotations. 
