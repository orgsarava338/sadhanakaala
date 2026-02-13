import toast from "react-hot-toast";

class Toaster {

    showError(message: string) {
        toast.error(message);
    }
    
    showSuccess(message: string) {
        toast.success(message);
    }
    
    showWarning(message: string) {
        toast(message, { icon: "⚠️" });
    }
}

const toaster = new Toaster();
export default toaster;