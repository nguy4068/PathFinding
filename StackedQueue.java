import java.util.Stack;

public class StackedQueue<T> {
    Stack1Gen<T> app = new Stack1Gen<>();
    int size = 0;
    public void enqueue(T element){
        app.push(element);
        size++;
    }
    public T dequeue(){
        Stack1Gen<T> app1 = new Stack1Gen<>();
        T data = null;
        for (int i = 0; i < size; i ++){
            data = app.pop();
            if ( i < size -1) {
                app1.push(data);
            }
        }
        for (int i = 0; i < size - 1;i++){
            T data1 = app1.pop();
            app.push(data1);
        }
        size--;

        return data;

    }
}
