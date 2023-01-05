package com.hemant.k8.resourceeater;


import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceEaterController {

  boolean eatingMemory = true;
  boolean eatingCPU = true;

  @GetMapping("/eat/memory/stop")
  public void stopEatingMemory() throws InterruptedException {
    eatingMemory = false;
  }

  @GetMapping("/eat/memory")
  public void startEatingMemory() throws InterruptedException {
/*    System.out.println("goit");
    System.out.println("Total memory (mb): " +
        Runtime.getRuntime().totalMemory() / ((1000 * 1000)));*/
    eatingMemory = true;
    List<byte[]> v = new ArrayList<>();
    while (eatingMemory) {
      byte b[] = new byte[1048576];
      v.add(b);
      Runtime rt = Runtime.getRuntime();
      System.out.println("free memory in mb: " + rt.freeMemory() / (1000 * 1000));
      Thread.sleep(100);
    }
  }

  @GetMapping("/eat/cpu")
  public void startEatingCPU() {
    double start = 0;
    eatingCPU = true;
    while (eatingCPU) {
      System.out.println("eating cup " + start);
      double x = Math.cos(start++);
    }
  }

  @GetMapping("/eat/cpu/stop")
  public void stopEatingCPU() {
    eatingCPU = false;
  }

}
