import useAuth from "@/auth/useAuth";
import toaster from "@/utils/toast.util";

export function handleApiError(error: any) {
    const { logout } = useAuth()

  // Backend business errors
  if (error.type === "BUSINESS" && error.errors) {
    error.errors.forEach((e: ApiError) => {
      toaster.showError(e.message);
    });
    return;
  }

  // HTTP auth errors
  if (error.type === "HTTP") {

    if (error.status === 401) {
      logout();
      toaster.showError("Session expired. Please login again.");
      return;
    }

    if (error.status === 403) {
      toaster.showError("You don't have permission.");
      return;
    }

    toaster.showError("Server error occurred.");
    return;
  }

  // Network
  if (error.type === "NETWORK") {
    toaster.showError(error.message);
  }
}
