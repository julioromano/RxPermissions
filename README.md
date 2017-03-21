# RxPermissions

An Android library to get Android M's runtime permissions with a nice and clean [RxJava](https://github.com/ReactiveX/RxJava) API.

## Features
 - RxJava2 API: Get Android M's runtime permissions using io.reactivex.Observable and compose it with all Rx's operators.

## Installation

Add this to your module's build.gradle:

```groovy
dependencies {
    compile 'net.kjulio.rxpermissions:rxpermissions:1.0.0'
}
```

## Usage

```java
RxPermissions.with(context)
     .requestPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
     .subscribe(...);
```

## License

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
